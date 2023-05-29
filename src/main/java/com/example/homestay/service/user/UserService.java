package com.example.homestay.service.user;

import com.example.homestay.model.DTO.ICountRole;
import com.example.homestay.model.DTO.UserPrinciple;
import com.example.homestay.model.Homes;
import com.example.homestay.model.Users;
import com.example.homestay.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return UserPrinciple.build(userOptional.get());
        }
        return null;
    }
    @Override
    public void sendVerificationEmail(Users user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Verify your account");
        mailMessage.setText("Click on the link to verify your account: "
                + "http://localhost:8080/verify?token=" + user.getVerificationToken());
        javaMailSender.send(mailMessage);
    }
    @Override
    public Optional<Users> findByVerificationToken(String verificationToken) {
        return userRepository.findByVerificationToken(verificationToken);
    }

    @Override
    public void verifyAccount(String verificationToken) {
        Optional<Users> optionalUser = userRepository.findByVerificationToken(verificationToken);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setVerified(true);
            user.setVerificationToken(null);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid verification token.");
        }
    }

    @Override
    public Users findByUsername(String name) {
        return userRepository.findByUsername(name).get();
    }

    @Override
    public Iterable<ICountRole> getRoleNumber() {
        return userRepository.getRoleNumber();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Set<Homes> findHomesById(Long userId) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return user.getHomes();
        }
        return Collections.emptySet();
    }
}

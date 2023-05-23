package com.example.homestay.service.user;

import com.example.homestay.model.DTO.ICountRole;
import com.example.homestay.model.Homes;
import com.example.homestay.model.Users;
import com.example.homestay.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.Set;

public interface IUserService extends IGeneralService<Users>, UserDetailsService {
    Users findByUsername(String name);

    Iterable<ICountRole> getRoleNumber();

    Boolean existsByUsername(String username);

    Optional<Users> findByVerificationToken(String verificationToken);

    void verifyAccount(String verificationToken);

    void sendVerificationEmail(Users user);

    Boolean existsByEmail(String email);

    Set<Homes> findHomesById(Long userId);

}

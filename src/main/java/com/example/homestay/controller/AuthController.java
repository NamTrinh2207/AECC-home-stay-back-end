package com.example.homestay.controller;

import com.example.homestay.model.DTO.ICountRole;
import com.example.homestay.model.DTO.JwtResponse;
import com.example.homestay.model.DTO.request.SignInForm;
import com.example.homestay.model.DTO.request.SignUpForm;
import com.example.homestay.model.DTO.response.ResponseMessage;
import com.example.homestay.model.Roles;
import com.example.homestay.model.Users;
import com.example.homestay.service.jwt.JwtService;
import com.example.homestay.service.role.IRoleService;
import com.example.homestay.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;


    //create user
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpForm user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("the username existed! please try again !"), HttpStatus.OK);
        }
        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("the email existed! please try again !"), HttpStatus.OK);
        }
        Users users = new Users(user.getUsername(),user.getPassword(),user.getEmail());
        Set<String> roleNames = user.getRoles();
        Set<Roles> roles = roleService.getRolesByName(roleNames);
        users.setRoles(roles);
        userService.save(users);
        userService.sendVerificationEmail(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>("Account verified successfully.", HttpStatus.OK);
    }

    /**
     * Xác thực thông tin đăng nhập và tạo JWT token cho người dùng.
     *
     * @param user thông tin đăng nhập của người dùng
     * @return ResponseEntity chứa thông tin JWT token và thông tin người dùng
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody SignInForm user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users currentUser = userService.findByUsername(user.getUsername());
        JwtResponse jwtResponse = new JwtResponse(jwt,currentUser.getId(), currentUser.getName(),
                currentUser.getAvatar(), currentUser.getUsername(), userDetails.getAuthorities());
        return ResponseEntity.ok(jwtResponse);
    }


    //edit user
    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable Long id, @RequestBody Users user) {
        Optional<Users> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            user.setId(id);
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listRole")
    public ResponseEntity<Iterable<ICountRole>> hello() {
        Iterable<ICountRole> iCountRoles = userService.getRoleNumber();
        return new ResponseEntity<>(iCountRoles, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return new ResponseEntity<>("User", HttpStatus.OK);
    }

}

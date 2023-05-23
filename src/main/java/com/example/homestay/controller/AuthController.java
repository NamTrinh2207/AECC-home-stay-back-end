package com.example.homestay.controller;

import com.example.homestay.model.DTO.ICountRole;
import com.example.homestay.model.DTO.JwtResponse;
import com.example.homestay.model.DTO.request.SignInForm;
import com.example.homestay.model.DTO.request.SignUpForm;
import com.example.homestay.model.DTO.response.ResponseMessage;
import com.example.homestay.model.Homes;
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

import java.util.List;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpForm user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("tên người dùng đã tồn tại! vui lòng thử lại !"), HttpStatus.ACCEPTED);
        }
        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("email đã tồn tại! vui lòng thử lại !"), HttpStatus.ACCEPTED);
        }
        Users users = user.toUser();
        Set<String> roleNames = user.getRoles();
        Set<Roles> roles = roleService.getRolesByName(roleNames);
        users.setRoles(roles);
        userService.save(users);
        userService.sendVerificationEmail(users);
        return new ResponseEntity<>(new ResponseMessage("Vui lòng truy cập email để xác nhận đăng ký"), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>(new ResponseMessage("Tài khoản được xác minh thành công."), HttpStatus.OK);
    }

    /**
     * Xác thực thông tin đăng nhập và tạo JWT token cho người dùng.
     *
     * @param user thông tin đăng nhập của người dùng
     * @return ResponseEntity chứa thông tin JWT token và thông tin người dùng
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInForm user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Users currentUser = userService.findByUsername(user.getUsername());

            if (!currentUser.isVerified()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseMessage("Tài khoản chưa được xác nhận"));
            }
            JwtResponse jwtResponse = new JwtResponse(jwt, currentUser.getId(), currentUser.getName(),
                    currentUser.getAvatar(), currentUser.getUsername(), userDetails.getAuthorities());
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage("Sai tài khoản mật khẩu hoặc email của bạn chưa được xác nhận !"));
        }
    }

    //edit user
    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable Long id, @RequestBody Users updatedUser) {
        Optional<Users> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            if (updatedUser.getAvatar() != null) {
                existingUser.setAvatar(updatedUser.getAvatar());
            }

            return new ResponseEntity<>(userService.save(existingUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("change/{id}")
    public ResponseEntity<?> changePass(@PathVariable Long id, @RequestBody Users users) {
        Optional<Users> usersOptional = userService.findById(id);
        if (usersOptional.isPresent()) {
            Users existingUser = usersOptional.get();
            if (!existingUser.getPassword().equals(users.getOldPassword())) {
                return ResponseEntity.badRequest().body("Mật khẩu hiện tại không chính xác.");
            }
            if (users.getPassword().equals(users.getConfirmPassword())) {
                existingUser.setPassword(users.getPassword());
                userService.save(existingUser);
                return new ResponseEntity<>("Đổi mật khẩu thành công", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Mật khẩu và xác nhận mật khẩu không khớp.", HttpStatus.ACCEPTED);
            }
        } else {
            return new ResponseEntity<>("Người dùng không tồn tại", HttpStatus.CREATED);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable Long id) {
        Optional<Users> userOptional = userService.findById(id);
        return userOptional.map(users
                -> new ResponseEntity<>(users, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/admin")
    public ResponseEntity<Iterable<ICountRole>> hello() {
        Iterable<ICountRole> iCountRoles = userService.getRoleNumber();
        return new ResponseEntity<>(iCountRoles, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return new ResponseEntity<>("tao là user đây", HttpStatus.OK);
    }

    @GetMapping("/{id}/homes")
    public Set<Homes> getUserHomes(@PathVariable Long id) {
        return userService.findHomesById(id);
    }

}

package com.example.homestay.controller;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.ICountRole;
import com.example.homestay.model.DTO.JwtResponse;
import com.example.homestay.model.DTO.request.SignInForm;
import com.example.homestay.model.DTO.request.SignUpForm;
import com.example.homestay.model.DTO.response.ResponseMessage;
import com.example.homestay.model.Homes;
import com.example.homestay.model.Roles;
import com.example.homestay.model.Users;
import com.example.homestay.service.booking.IBookingService;
import com.example.homestay.service.home.IHomeService;
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
import org.springframework.web.servlet.view.RedirectView;

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
    @Autowired
    private IHomeService homeService;
    @Autowired
    private IBookingService bookingService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpForm user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("tên người dùng đã tồn tại! vui lòng thử lại !"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("email đã tồn tại! vui lòng thử lại !"), HttpStatus.BAD_REQUEST);
        }
        Users users = user.toUser();
        Set<String> roleNames = user.getRoles();
        Set<Roles> roles = roleService.getRolesByName(roleNames);
        users.setRoles(roles);
        users.setAvatar("https://cdn-icons-png.flaticon.com/512/149/149071.png");
        userService.save(users);
        userService.sendVerificationEmail(users);
        return new ResponseEntity<>(new ResponseMessage("Vui lòng truy cập email để xác nhận đăng ký"), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public RedirectView verifyAccount(@RequestParam String token) {
        userService.verifyAccount(token);
        return new RedirectView("http://localhost:3000/login?success=true");
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
                return new ResponseEntity<>(new ResponseMessage("Tài khoản chưa được xác nhận vui lòng truy cập vào email đăng ký để kích hoạt tài khoản"),HttpStatus.ACCEPTED);
            }
            JwtResponse jwtResponse = new JwtResponse(jwt, currentUser.getId(), currentUser.getName(),
                    currentUser.getAvatar(), currentUser.getUsername(), userDetails.getAuthorities());
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage("Sai tài khoản hoặc mật khẩu !"));
        }
    }

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
                return new ResponseEntity<>(new ResponseMessage("Mật khẩu hiện tại không chính xác."), HttpStatus.BAD_REQUEST);
            }
            if (users.getPassword().equals(users.getConfirmPassword())) {
                existingUser.setPassword(users.getPassword());
                userService.save(existingUser);
                return new ResponseEntity<>(new ResponseMessage("Đổi mật khẩu thành công"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Mật khẩu và xác nhận mật khẩu không khớp."), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ResponseMessage("Người dùng không tồn tại"), HttpStatus.BAD_REQUEST);
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

    @GetMapping("/list")
    public ResponseEntity<Iterable<Users>> listUsers(){
        Iterable<Users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/{id}/homes")
    public List<Homes> getUserHomes(@PathVariable Long id) {
        return homeService.findByUsers(id);
    }


    @GetMapping("/{id}/booking/unchecked")
    public ResponseEntity<List<Booking>> getUncheckedBooking(@PathVariable Long id) {
        List<Booking> bookings = bookingService.getUncheckedBooking(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}/booking/checked")
    public ResponseEntity<List<Booking>> getCheckedBooking(@PathVariable Long id) {
        List<Booking> bookings = bookingService.getCheckedBooking(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}/booking/cancelRequest")
    public ResponseEntity<List<Booking>> getCancelRequest(@PathVariable Long id) {
        List<Booking> bookings = bookingService.getCancelRequest(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
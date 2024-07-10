package com.nuwaish.crm_system_backend_springboot.user;

import com.nuwaish.crm_system_backend_springboot.response.AuthResponse;
import com.nuwaish.crm_system_backend_springboot.securityConfig.JwtProvider;
import com.nuwaish.crm_system_backend_springboot.securityConfig.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImplementation customUserDetails;

    @Autowired(required = true)
    private UserService userService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String mobile = user.getMobile();
        String role = user.getRole();

        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Email is already used with another account");
            authResponse.setStatus(false);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setMobile(mobile);
        createdUser.setRole(role);
        createdUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(createdUser);
        userRepository.save(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    @PostMapping("signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        tokenBlacklistService.blacklistToken(jwtToken);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Logout Success");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @GetMapping("/user/details")
    private ResponseEntity<User> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {

            throw new BadCredentialsException("Invalid username and password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

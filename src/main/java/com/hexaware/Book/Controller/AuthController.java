package com.hexaware.Book.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.Book.Dao.UserRepository;
import com.hexaware.Book.Entity.AuthenticationRequest;
import com.hexaware.Book.Entity.AuthenticationResponse;
import com.hexaware.Book.Entity.JwtUtil;
import com.hexaware.Book.Entity.SignupRequest;
import com.hexaware.Book.Entity.User;
import com.hexaware.Book.Service.AuthService;
import com.hexaware.Book.Service.UserService;




@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private  AuthService authService;
	@Autowired
    private  AuthenticationManager authenticationManager;
	@Autowired
    private  UserService userService;
	@Autowired
    private  JwtUtil jwtUtil;
	@Autowired
    private  UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer already exists", HttpStatus.NOT_ACCEPTABLE);

        User createdCustomerDto = authService.createUser(signupRequest);

        if (createdCustomerDto == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Incorrect Email Or Password.");
        }
        
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return ResponseEntity.ok(authenticationResponse);
    }

    }

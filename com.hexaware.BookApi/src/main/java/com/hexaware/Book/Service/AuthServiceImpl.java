package com.hexaware.Book.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.Book.Dao.UserRepository;
import com.hexaware.Book.Entity.SignupRequest;
import com.hexaware.Book.Entity.User;
import com.hexaware.Book.Enum.UserRole;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

        // Set a default role if none is provided
        if (signupRequest.getUserRole() == null) {
            user.setUserRole(UserRole.USER); // Default role to USER
        } else {
            user.setUserRole(signupRequest.getUserRole());
        }

        User createdUser = userRepository.save(user);

        // Return only necessary fields to the client (exclude password)
        User userDto = new User();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getUserRole());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}


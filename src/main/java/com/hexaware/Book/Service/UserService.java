package com.hexaware.Book.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	UserDetailsService userDetailsService();


}

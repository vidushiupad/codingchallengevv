package com.hexaware.Book.Service;

import com.hexaware.Book.Entity.SignupRequest;
import com.hexaware.Book.Entity.User;

public interface AuthService {
	
	User createUser(SignupRequest signupRequest);
	
	boolean hasCustomerWithEmail(String Email);

}

package com.app.quantitymeasurementapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurementapp.dto.dtoRequest.LoginRequest;
import com.app.quantitymeasurementapp.dto.dtoRequest.RegisterRequest;
import com.app.quantitymeasurementapp.dto.dtoResponse.AuthResponse;
import com.app.quantitymeasurementapp.dto.dto_conversion.UserDTO;
import com.app.quantitymeasurementapp.entity.User;
import com.app.quantitymeasurementapp.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	/*
	 * Constructor Injection
	 */
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	
	
	//register
	public AuthResponse registerUser(RegisterRequest registerRequest) {
		//step 1: validate user duplication
		User userdb = userRepository.findByEmail(registerRequest.getEmail()).orElse(null);
		if(userdb!=null) {
			throw new RuntimeException("User alredy registered with this eamil!");
		}
		
		registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userRepository.save(new UserDTO().toUser(registerRequest));
		String token = jwtService.genrateToken(registerRequest.getEmail());
		return new AuthResponse(token,"User Register success");
	}
	
	
	//login
	public AuthResponse loginUser(LoginRequest loginRequest) {
		User userdb = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new RuntimeException("User not found!"));
		
		if(!passwordEncoder.matches(loginRequest.getPassword(), userdb.getPassword())) {
			throw new RuntimeException("Invalid Password!");
		}
		String token = jwtService.genrateToken(loginRequest.getEmail());
		return new AuthResponse(token,"Login Success");
	}
}
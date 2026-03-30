package com.app.quantitymeasurementapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurementapp.dto.dtoRequest.LoginRequest;
import com.app.quantitymeasurementapp.dto.dtoRequest.RegisterRequest;
import com.app.quantitymeasurementapp.dto.dtoResponse.AuthResponse;
import com.app.quantitymeasurementapp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth/user")
@Tag(name = "User Authentication", description = "User must have to register or login for using our service")
public class UserController {
	
	
	private UserService userService;
	
	/*
	 * Constructor Injection 
	 */

	public UserController(UserService userService) {
		this.userService = userService;
	}

	private static final String RegisterUser1 = """
			{
				"username":"ravi", "email":"ravi@gmail.com", "password":"ravi1234", "role":"USER"
			}
			""";
	
	private static final String RegisterUser2 = """
			{
				"username":"sonu", "email":"sonu@gmail.com", "password":"sonu1234", "role":"USER"
			}
			""";
	private static final String LoginUser1 = """
			{
				"email":"ravi@gmail.com", "password":"ravi1234"
			}
			""";
	private static final String LoginUser2 = """
			{
				"email":"sonu@gamil.com", "password":"sonu1234"
			}
			""";
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome! It is working.";
	}
	
	@PostMapping("/register")
	@Operation(summary = "Register",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(examples = {
				@ExampleObject(name = "User 1", value = RegisterUser1),
				@ExampleObject(name = "User 2", value = RegisterUser2),
			})
		)
	)
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {		
		return ResponseEntity.ok(userService.registerUser(registerRequest));
	}
	
	@PostMapping("/login")
	@Operation(summary = "Login",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(examples = {
				@ExampleObject(name = "User 1", value = LoginUser1),
				@ExampleObject(name = "User 2", value = LoginUser2),
			})
		)
	)
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userService.loginUser(loginRequest));
	}
}
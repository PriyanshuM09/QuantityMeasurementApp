package com.app.quantitymeasurementapp.dto.dto_conversion;

import com.app.quantitymeasurementapp.dto.dtoRequest.RegisterRequest;
import com.app.quantitymeasurementapp.entity.User;

public class UserDTO {
	public User toUser(RegisterRequest registerRequest){
		return new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getRole());
	}
}
package com.furkandogan.service;

import com.furkandogan.dto.AuthRequest;
import com.furkandogan.dto.AuthResponse;
import com.furkandogan.dto.DtoUser;
import com.furkandogan.dto.RefreshTokenRequest;
import com.furkandogan.model.RefreshToken;

public interface IAuthenticationService {

	public DtoUser register (AuthRequest input );
	
	public AuthResponse authenticate (AuthRequest input);
	
	public AuthResponse refreshToken (RefreshTokenRequest input);
	
}

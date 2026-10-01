package com.furkandogan.controller;

import com.furkandogan.dto.AuthRequest;
import com.furkandogan.dto.AuthResponse;
import com.furkandogan.dto.DtoUser;
import com.furkandogan.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {
	
	public RootEntity<DtoUser> register (AuthRequest input);
	
	public RootEntity<AuthResponse> authenticate (AuthRequest input);
	
	public RootEntity<AuthResponse> refreshToken (RefreshTokenRequest input);

}

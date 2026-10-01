package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestAuthenticationController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.AuthRequest;
import com.furkandogan.dto.AuthResponse;
import com.furkandogan.dto.DtoUser;
import com.furkandogan.dto.RefreshTokenRequest;
import com.furkandogan.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController{

	@Autowired
	private IAuthenticationService authenticationService;
	
	@PostMapping ("/register")
	@Override
	public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest input) {
	
		return ok (authenticationService.register(input));
	}

	@PostMapping ("/authenticate")
	@Override
	public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {
	
		return ok(authenticationService.authenticate(input));
		
	}

	@PostMapping ("/refreshToken")
	@Override
	public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest input) {
		
		return ok (authenticationService.refreshToken(input));
	}
	
	

}

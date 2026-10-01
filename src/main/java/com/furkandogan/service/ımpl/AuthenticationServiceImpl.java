package com.furkandogan.service.ımpl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.AuthRequest;
import com.furkandogan.dto.AuthResponse;
import com.furkandogan.dto.DtoUser;
import com.furkandogan.dto.RefreshTokenRequest;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.jwt.JWTService;
import com.furkandogan.model.RefreshToken;
import com.furkandogan.model.User;
import com.furkandogan.repository.RefreshTokenRepository;
import com.furkandogan.repository.UserRepository;
import com.furkandogan.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService{
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JWTService jwtService;
	
	private User createUser(AuthRequest input) {
		User user = new User();
		user.setCreateTime(new Date());
		user.setUsername(input.getUsername());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		
		return user;
	}
	
	private RefreshToken createResfreshToken (User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setCreateTime(new Date());
		refreshToken.setExpiredDate(new Date(System.currentTimeMillis()+1000*60*60*4));
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setUser(user);
		return refreshToken;
	}
	
	
	@Override
	public DtoUser register(AuthRequest input) {
		DtoUser dtoUser = new DtoUser();
		User savedUser = userRepository.save(createUser(input));
		
		BeanUtils.copyProperties(savedUser, dtoUser);
		
		return dtoUser;
	}


	@Override
	public AuthResponse authenticate(AuthRequest input) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
			
			authenticationProvider.authenticate(authenticationToken);
			
			Optional<User> opUser = userRepository.findByUsername(input.getUsername());
			
			String accessToken = jwtService.generateToken(opUser.get());
			
		  //  RefreshToken refreshToken	=createResfreshToken(opUser.get());
			
		RefreshToken savedRefreshToken =	refreshTokenRepository.save(createResfreshToken(opUser.get()));
			
		return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
		
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID , e.getMessage()));
		}
	
	}
	
	public boolean isValidRefreshToken (Date expiredDate) {
		return new Date().before(expiredDate);
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest input) {
		Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());
		if (optRefreshToken.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND,input.getRefreshToken()));
		}
		if (!isValidRefreshToken(optRefreshToken.get().getExpiredDate())) {
			throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPİRED,input.getRefreshToken()));

		}
		User user = optRefreshToken.get().getUser();
		String accessToken = jwtService.generateToken(user);
		RefreshToken savedRefreshToken = refreshTokenRepository.save(createResfreshToken(user));
		return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
	}
	
	

}

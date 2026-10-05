package com.furkandogan.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST(
	        "1004", "Kayıt bulunamadı", HttpStatus.NOT_FOUND),

	TOKEN_IS_EXPIRED(
	        "1005", "Token Süresi bitmiştir", HttpStatus.UNAUTHORIZED),

	GENERAL_EXCEPTİON(
	        "9999", "Genel bir hata alındı", HttpStatus.INTERNAL_SERVER_ERROR),

	USERNAME_NOT_FOUND(
	        "1006", "Username bulunamadı", HttpStatus.NOT_FOUND),

	USERNAME_OR_PASSWORD_INVALID(
	        "1007", "Kullanıcı adı şifre geçersiz.", HttpStatus.UNAUTHORIZED),

	REFRESH_TOKEN_IS_EXPİRED(
	        "1009", "Refresh token süresi bitmiş.", HttpStatus.UNAUTHORIZED),

	REFRESH_TOKEN_NOT_FOUND(
	        "1008", "Refresh token bulunamadı", HttpStatus.NOT_FOUND),

	CUSTOMER_AMOUNT_IS_NOT_ENOUGH(
	        "1011", "Müşterinin parası yeterli değildir", HttpStatus.BAD_REQUEST),

	CAR_STATUS_IS_SALED(
	        "1012", "Bu araç zaten satılmış", HttpStatus.CONFLICT),

	CURRENCY_RATES_IS_OCCURED(
	        "1010", "Döviz kodu alınamadı", HttpStatus.INTERNAL_SERVER_ERROR);
	
	
	private String code;
	
	private String message;
	
	private HttpStatus httpStatus;
	
	MessageType (String code , String message , HttpStatus httpStatus) {
		this.code = code;
		
		this.message = message;
		
		this.httpStatus = httpStatus;
	}

}

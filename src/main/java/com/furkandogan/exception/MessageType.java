package com.furkandogan.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST("1004","Kayıt bulunamadı"),
	TOKEN_IS_EXPIRED ("1005" , "Token Süresi bitmiştir"),
	GENERAL_EXCEPTİON ("9999" , "Genel bir hata alındı"),
	USERNAME_NOT_FOUND ("1006" , "Username bulunamadı"),
	USERNAME_OR_PASSWORD_INVALID ("1007","Kullanıcı adı şifre geçersiz."),
	REFRESH_TOKEN_IS_EXPİRED ("1009" , "Refresh token süresi bitmiş."),
	REFRESH_TOKEN_NOT_FOUND ("1008", "Refresh token bulunamadı"),
	CUSTOMER_AMOUNT_IS_NOT_ENOUGH ("1011" , "Müşterinin parası yeterli değildir"),
	CAR_STATUS_IS_SALED ("1012" , "Bu araç zaten satılmış"),
	CURRENCY_RATES_IS_OCCURED ("1010" , "Döviz kodu alınamadı");
	
	
	private String code;
	
	private String message;
	
	MessageType (String code , String message) {
		this.code = code;
		
		this.message = message;
	}

}

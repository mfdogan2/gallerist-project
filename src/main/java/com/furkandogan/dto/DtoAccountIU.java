package com.furkandogan.dto;

import java.math.BigDecimal;

import com.furkandogan.enums.CurrencyType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoAccountIU {
	
	@NotNull
	private String accountNo;
	
	@NotNull
	private String iban;
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private CurrencyType currencyType;

}

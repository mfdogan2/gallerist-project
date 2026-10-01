package com.furkandogan.dto;

import java.math.BigDecimal;

import com.furkandogan.enums.CurrencyType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoAccount extends DtoBase {
	
	
	private String accountNo;
	
	
	private String iban;
	

	private BigDecimal amount;
	

	private CurrencyType currencyType;

}

package com.furkandogan.dto;

import java.math.BigDecimal;

import com.furkandogan.enums.CarStatusType;
import com.furkandogan.enums.CurrencyType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoCarUI {

	@NotNull
	private String plaka;
	@NotNull
	private String brand;
	@NotNull
	private String model;
	@NotNull
	private Integer productionYear;
	@NotNull
	private BigDecimal price;
	@NotNull
	private CurrencyType currencyType;
	@NotNull
	private BigDecimal damageType;
	@NotNull
	private CarStatusType carStatusType;
	
}

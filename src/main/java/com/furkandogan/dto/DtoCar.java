package com.furkandogan.dto;

import java.math.BigDecimal;

import com.furkandogan.enums.CarStatusType;
import com.furkandogan.enums.CurrencyType;

import lombok.Data;

@Data
public class DtoCar extends DtoBase {
	
	
	private String plaka;
	
	private String brand;
	
	private String model;
	
	private Integer productionYear;
	
	private BigDecimal price;
	
	private CurrencyType currencyType;
	
	private BigDecimal damageType;
	
	private CarStatusType carStatusType;

}

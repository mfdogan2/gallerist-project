package com.furkandogan.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoSaledCar extends DtoBase {

	private DtoCustomer customer;
	
	private DtoGallerist gallerist;
	
	private DtoCar car;
	
}

package com.furkandogan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoGalleristCar extends DtoBase{


	private DtoGallerist gallerist;

	private DtoCar car;
	
}

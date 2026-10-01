package com.furkandogan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoSaledCarIU {

	@NotNull
	private Long customerId;
	@NotNull
	private Long galleristId;
	@NotNull
	private Long carId;
	
}

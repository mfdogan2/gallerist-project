package com.furkandogan.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DtoAddressIU {

	@NotEmpty
	private String city;
	@NotEmpty
	private String district;
	@NotEmpty
	private String neighborhood;
	@NotEmpty
	private String street;
}

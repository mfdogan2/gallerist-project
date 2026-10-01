package com.furkandogan.dto;

import com.furkandogan.model.Address;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoGalleristIU {

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private Long addressId;
}

package com.furkandogan.dto;

import com.furkandogan.model.Address;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoGallerist extends DtoBase{
	
	
	private String firstName;
	
	
	private String lastName;
	
	
	private DtoAddress address;

}

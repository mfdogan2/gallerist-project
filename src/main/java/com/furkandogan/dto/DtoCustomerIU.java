package com.furkandogan.dto;

import java.sql.Date;

import com.furkandogan.model.Account;
import com.furkandogan.model.Address;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoCustomerIU {
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String tckn;
	
	@NotNull
	private Date birthOfDate;
	
	@NotNull
	private Long addressId;
	
	@NotNull
	private Long accountId;

}

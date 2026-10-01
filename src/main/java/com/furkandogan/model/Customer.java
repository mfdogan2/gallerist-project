package com.furkandogan.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.furkandogan.enums.CurrencyType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer  extends BaseEntity{
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "tckn")
	private String tckn;
	
	@Column(name = "birth_of_date")
	private Date birthOfDate;
	
	@OneToOne
	private Address address;
	
	@OneToOne
	private Account account;
	
	

}

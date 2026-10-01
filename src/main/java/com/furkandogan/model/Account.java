package com.furkandogan.model;

import java.math.BigDecimal;

import com.furkandogan.enums.CurrencyType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity{
	
	@Column (name = "account_no")
	private String accountNo;
	
	@Column (name = "iban")
	private String iban;
	
	@Column (name = "amount")
	private BigDecimal amount;
	
	@Column (name = "currency_type")
	@Enumerated (EnumType.STRING)
	private CurrencyType currencyType;
	

}

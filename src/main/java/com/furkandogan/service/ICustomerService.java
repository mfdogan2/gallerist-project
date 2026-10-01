package com.furkandogan.service;

import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoCustomerIU;

public interface ICustomerService {

	public DtoCustomer saveCustomer (DtoCustomerIU dtoCustomerIU);
}

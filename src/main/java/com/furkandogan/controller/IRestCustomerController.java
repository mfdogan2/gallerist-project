package com.furkandogan.controller;

import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoCustomerIU;

public interface IRestCustomerController  {
	
	public RootEntity<DtoCustomer> saveCustomer (DtoCustomerIU dtoCustomerIU);

}

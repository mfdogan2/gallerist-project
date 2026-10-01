package com.furkandogan.service;

import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoCustomerIU;
import com.furkandogan.dto.DtoGallerist;
import com.furkandogan.dto.DtoGalleristIU;

public interface IGalleristService {

	public DtoGallerist saveGallerist (DtoGalleristIU dtoGalleristIU);
	
}

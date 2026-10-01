package com.furkandogan.controller;

import com.furkandogan.dto.DtoAddress;
import com.furkandogan.dto.DtoAddressIU;

public interface IRestAddressController {

	public RootEntity <DtoAddress> saveAddress (DtoAddressIU dtoAddressIU);
}

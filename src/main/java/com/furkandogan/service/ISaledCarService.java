package com.furkandogan.service;

import com.furkandogan.dto.DtoSaledCar;
import com.furkandogan.dto.DtoSaledCarIU;

public interface ISaledCarService {

	public DtoSaledCar buyCar (DtoSaledCarIU dtoSaledCarIU);
	
}

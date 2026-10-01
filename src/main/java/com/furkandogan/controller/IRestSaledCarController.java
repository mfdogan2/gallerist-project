package com.furkandogan.controller;

import com.furkandogan.dto.DtoSaledCar;
import com.furkandogan.dto.DtoSaledCarIU;

public interface IRestSaledCarController {
	
	public RootEntity<DtoSaledCar> buyCar (DtoSaledCarIU dtoSaledCarIU); 

}

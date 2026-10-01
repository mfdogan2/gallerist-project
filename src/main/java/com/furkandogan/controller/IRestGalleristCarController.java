package com.furkandogan.controller;

import com.furkandogan.dto.DtoGalleristCar;
import com.furkandogan.dto.DtoGalleristCarIU;

public interface IRestGalleristCarController {

	RootEntity<DtoGalleristCar> saveGalleristCar (DtoGalleristCarIU dtoGalleristCarIU);
	
}

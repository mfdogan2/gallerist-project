package com.furkandogan.service;

import com.furkandogan.dto.DtoGalleristCar;
import com.furkandogan.dto.DtoGalleristCarIU;
import com.furkandogan.dto.DtoGalleristIU;

public interface IGalleristCarService {
	
	public DtoGalleristCar saveGalleristCar (DtoGalleristCarIU dtoGalleristCarIU);

}

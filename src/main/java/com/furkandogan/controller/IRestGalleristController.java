package com.furkandogan.controller;

import com.furkandogan.dto.DtoGallerist;
import com.furkandogan.dto.DtoGalleristIU;

public interface IRestGalleristController {
	
	public RootEntity<DtoGallerist> saveGallerist (DtoGalleristIU dtoGalleristIU);

}

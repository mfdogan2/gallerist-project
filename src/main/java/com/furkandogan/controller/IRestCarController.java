package com.furkandogan.controller;

import com.furkandogan.dto.DtoCar;
import com.furkandogan.dto.DtoCarUI;

public interface IRestCarController {

	public RootEntity<DtoCar> saveCar (DtoCarUI dtoCarUI);
}

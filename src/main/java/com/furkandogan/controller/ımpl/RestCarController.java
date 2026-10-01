package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestCarController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoCar;
import com.furkandogan.dto.DtoCarUI;
import com.furkandogan.service.ICarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/rest/api/car")
public class RestCarController extends RestBaseController implements IRestCarController{

	@Autowired
	private ICarService carService;
	
	@PostMapping ("/save")
	@Override
	public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarUI dtoCarUI) {
		
		
		return ok (carService.saveCar(dtoCarUI));
	}

}

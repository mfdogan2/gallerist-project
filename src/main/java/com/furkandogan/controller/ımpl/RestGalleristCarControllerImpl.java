package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestGalleristCarController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoGalleristCar;
import com.furkandogan.dto.DtoGalleristCarIU;
import com.furkandogan.service.IGalleristCarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/rest/api/galleristcar")
public class RestGalleristCarControllerImpl extends RestBaseController implements IRestGalleristCarController {

	@Autowired
	private IGalleristCarService galleristCarService;
	
	
	@PostMapping ("/save")
	@Override
	public RootEntity<DtoGalleristCar> saveGalleristCar(@Valid @RequestBody DtoGalleristCarIU dtoGalleristCarIU) {
		
		return ok(galleristCarService.saveGalleristCar(dtoGalleristCarIU));
	}

	
}

package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestSaledCarController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoSaledCar;
import com.furkandogan.dto.DtoSaledCarIU;
import com.furkandogan.service.ISaledCarService;


import jakarta.validation.Valid;

@RestController
@RequestMapping ("/rest/api/saled-car")
public class RestSaledCarControllerImpl extends RestBaseController implements IRestSaledCarController {
	
	@Autowired
	private ISaledCarService saledCarService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoSaledCar> buyCar(@Valid @RequestBody DtoSaledCarIU dtoSaledCarIU) {
		
		return ok(saledCarService.buyCar(dtoSaledCarIU));
	}
 
}

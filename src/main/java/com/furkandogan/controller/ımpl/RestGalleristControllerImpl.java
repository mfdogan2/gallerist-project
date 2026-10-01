package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestGalleristController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoGallerist;
import com.furkandogan.dto.DtoGalleristIU;
import com.furkandogan.service.IGalleristService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/rest/api/gallerist")
public class RestGalleristControllerImpl extends RestBaseController implements IRestGalleristController{

	@Autowired
	private IGalleristService galleristService;
	
	@PostMapping ("/save")
	@Override
	public RootEntity<DtoGallerist> saveGallerist(@Valid @RequestBody DtoGalleristIU dtoGalleristIU) {
		
		return ok(galleristService.saveGallerist(dtoGalleristIU));
	}

}

package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestAddressController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoAddress;
import com.furkandogan.dto.DtoAddressIU;
import com.furkandogan.service.IAddressService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/rest/api/address")
public class RestAddressControllerImpl extends RestBaseController implements IRestAddressController{

	@Autowired
	private IAddressService addressService;
	
	@PostMapping ("/save")
	@Override
	public RootEntity<DtoAddress> saveAddress(@Valid @RequestBody DtoAddressIU dtoAddressIU) {
	
		return ok(addressService.saveAddress(dtoAddressIU));
	}

}

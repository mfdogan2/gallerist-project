package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestCustomerController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoCustomerIU;
import com.furkandogan.service.ICustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/rest/api/customer")
public class RestCustomerControllerImpl extends RestBaseController implements IRestCustomerController {

	@Autowired
	private ICustomerService customerService;
	
	@PostMapping ("/save")
	@Override
	public RootEntity<DtoCustomer> saveCustomer(@Valid @RequestBody DtoCustomerIU dtoCustomerIU) {
		
		return ok (customerService.saveCustomer(dtoCustomerIU));
	}

}

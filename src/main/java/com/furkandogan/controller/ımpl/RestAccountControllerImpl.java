package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestAccountController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.DtoAccount;
import com.furkandogan.dto.DtoAccountIU;
import com.furkandogan.service.IAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/rest/api/account")
public class RestAccountControllerImpl extends RestBaseController implements IRestAccountController{

	@Autowired
	private IAccountService accountService;
	
	@PostMapping ("/save")
	@Override
	public RootEntity<DtoAccount> saveAccount(@Valid @RequestBody DtoAccountIU dtoAccountIU) {
		
		
		return ok(accountService.saveAccount(dtoAccountIU));
	}
	
	

}

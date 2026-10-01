package com.furkandogan.controller;

import com.furkandogan.dto.DtoAccount;
import com.furkandogan.dto.DtoAccountIU;

public interface IRestAccountController {
	
	public RootEntity <DtoAccount> saveAccount (DtoAccountIU dtoAccountIU) ;

}

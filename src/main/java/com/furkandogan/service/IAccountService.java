package com.furkandogan.service;

import com.furkandogan.dto.DtoAccount;
import com.furkandogan.dto.DtoAccountIU;

public interface IAccountService {

	public DtoAccount saveAccount (DtoAccountIU dtoAccountIU);
}

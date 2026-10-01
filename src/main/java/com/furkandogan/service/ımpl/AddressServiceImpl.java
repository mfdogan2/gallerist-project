package com.furkandogan.service.ımpl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.DtoAddress;
import com.furkandogan.dto.DtoAddressIU;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.model.Address;
import com.furkandogan.repository.AddressRepository;
import com.furkandogan.service.IAddressService;

@Service
public class AddressServiceImpl implements IAddressService {

	
	@Autowired
	private AddressRepository addressRepository ;
	
	
	private Address createAddress (DtoAddressIU dtoAddressIU) {
	Address address = new Address ();
	address.setCreateTime(new Date());
	
	BeanUtils.copyProperties(dtoAddressIU, address);
	return address;
	}
	
	@Override
	public DtoAddress saveAddress(DtoAddressIU dtoAddressIU) {
	 
		DtoAddress dtoAddress = new DtoAddress();
		
		Address savedAddress = addressRepository.save(createAddress(dtoAddressIU));
		BeanUtils.copyProperties(savedAddress, dtoAddress);
		return dtoAddress;
		
	}
	
	

}

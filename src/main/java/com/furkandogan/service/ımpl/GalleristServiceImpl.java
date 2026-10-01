package com.furkandogan.service.ımpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.DtoAddress;
import com.furkandogan.dto.DtoGallerist;
import com.furkandogan.dto.DtoGalleristIU;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.model.Address;
import com.furkandogan.model.Gallerist;
import com.furkandogan.repository.AddressRepository;
import com.furkandogan.repository.GalleristRepository;
import com.furkandogan.service.IGalleristService;

@Service
public class GalleristServiceImpl implements IGalleristService{

	@Autowired
	private GalleristRepository galleristRepository;
	
	@Autowired AddressRepository addressRepository;
	
	public Gallerist createGallerist (DtoGalleristIU dtoGalleristIU) {
		Optional<Address> optAddress = addressRepository.findById(dtoGalleristIU.getAddressId());
		if (optAddress.isEmpty()) {
			throw new BaseException( new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristIU.getAddressId().toString()));
			
		}
		
		Gallerist gallerist = new Gallerist();
		gallerist.setCreateTime(new Date());
		BeanUtils.copyProperties(dtoGalleristIU, gallerist);
		
		gallerist.setAddress(optAddress.get());
		return gallerist;
		
	}
	
	@Override
	public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU) {
		
		DtoGallerist dtoGallerist = new DtoGallerist();
		DtoAddress dtoAddress = new DtoAddress();
		
		Gallerist savedGallerist = galleristRepository.save(createGallerist(dtoGalleristIU));
		
		BeanUtils.copyProperties(savedGallerist, dtoGallerist);
		BeanUtils.copyProperties(savedGallerist.getAddress(), dtoAddress);
		
		dtoGallerist.setAddress(dtoAddress);
		
		
		return dtoGallerist;
	}

}

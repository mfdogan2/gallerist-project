package com.furkandogan.service.ımpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.DtoAddress;
import com.furkandogan.dto.DtoCar;
import com.furkandogan.dto.DtoGallerist;
import com.furkandogan.dto.DtoGalleristCar;
import com.furkandogan.dto.DtoGalleristCarIU;
import com.furkandogan.dto.DtoGalleristIU;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.model.Car;
import com.furkandogan.model.Gallerist;
import com.furkandogan.model.GalleristCar;
import com.furkandogan.repository.CarRepository;
import com.furkandogan.repository.GalleristCarRepository;
import com.furkandogan.repository.GalleristRepository;
import com.furkandogan.service.IGalleristCarService;

@Service
public class GalleristCarServiceImpl implements IGalleristCarService{

	@Autowired
	private GalleristRepository galleristRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private GalleristCarRepository galleristCarRepository;
	
	public GalleristCar createGalleristCar (DtoGalleristCarIU dtoGalleristCarIU) {
		  Optional<Gallerist> optGallerist  = galleristRepository.findById(dtoGalleristCarIU.getGalleristId());
		if (optGallerist.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristCarIU.getGalleristId().toString()));
		}
		
		 Optional<Car> optCar =carRepository.findById(dtoGalleristCarIU.getCarId());
		 if (optCar.isEmpty()) {
			 throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristCarIU.getCarId().toString()));
			 
		 }
		 
		
		GalleristCar galleristCar = new GalleristCar();
		galleristCar.setCreateTime(new Date());
	//	BeanUtils.copyProperties(dtoGalleristCarIU, galleristCar);
		
		galleristCar.setCar(optCar.get());
		galleristCar.setGallerist(optGallerist.get());
		
		return galleristCar;
		
	}
	
	@Override
	public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {
		
		DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
	    DtoCar dtoCar = new DtoCar();
	    DtoGallerist dtoGallerist = new DtoGallerist();
	    
	    DtoAddress dtoAddress = new DtoAddress();
	    
	    GalleristCar savedGalerristCar = galleristCarRepository.save(createGalleristCar(dtoGalleristCarIU));
	    
	    BeanUtils.copyProperties(savedGalerristCar, dtoGalleristCar);
	    BeanUtils.copyProperties(savedGalerristCar.getCar(), dtoCar);
	    BeanUtils.copyProperties(savedGalerristCar.getGallerist(), dtoGallerist);
	    BeanUtils.copyProperties(savedGalerristCar.getGallerist().getAddress(), dtoAddress);
	    
	    dtoGallerist.setAddress(dtoAddress);
	    dtoGalleristCar.setCar(dtoCar);
	    dtoGalleristCar.setGallerist(dtoGallerist);
	    

		return dtoGalleristCar;
	}

}

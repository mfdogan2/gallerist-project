package com.furkandogan.service.ımpl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.DtoCar;
import com.furkandogan.dto.DtoCarUI;
import com.furkandogan.model.Car;
import com.furkandogan.model.Customer;
import com.furkandogan.repository.CarRepository;
import com.furkandogan.service.ICarService;
@Service
public class CarServiceImpl implements ICarService{

	@Autowired
	private CarRepository carRepository;
	
	public Car createCar (DtoCarUI dtoCarUI) {
		
		Car car = new Car();
		car.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoCarUI, car);
		return car;
		
	}
	
	
	@Override
	public DtoCar saveCar(DtoCarUI dtoCarUI) {
		DtoCar dtoCar = new DtoCar();
		Car savedCar = carRepository.save(createCar(dtoCarUI));
		
		BeanUtils.copyProperties(savedCar, dtoCar);
		
		return dtoCar;
	}

}

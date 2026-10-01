package com.furkandogan.service.ımpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.CurrencyRatesResponse;
import com.furkandogan.dto.DtoCar;
import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoGallerist;
import com.furkandogan.dto.DtoSaledCar;
import com.furkandogan.dto.DtoSaledCarIU;
import com.furkandogan.enums.CarStatusType;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.model.Car;
import com.furkandogan.model.Customer;
import com.furkandogan.model.SaledCar;
import com.furkandogan.repository.CarRepository;
import com.furkandogan.repository.CustomerRepository;
import com.furkandogan.repository.GalleristRepository;
import com.furkandogan.repository.SaledCarRepository;
import com.furkandogan.service.ICurrencyRatesService;
import com.furkandogan.service.ISaledCarService;
import com.furkandogan.utils.DateUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaledCarServiceImpl implements ISaledCarService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private GalleristRepository galleristRepository;
	
	@Autowired
	private ICurrencyRatesService currencyRatesService;
	
	@Autowired
	private SaledCarRepository saledCarRepository;
	
	public BigDecimal convertCustomerAmountToUsd (Customer customer) {
		
		CurrencyRatesResponse currencyRatesResponse = currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
        BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());	
        
        BigDecimal customerUsdAmount = customer.getAccount().getAmount().divide(usd, 2, RoundingMode.HALF_UP);
        
        return customerUsdAmount;
	}
	
	public boolean checkAmount (DtoSaledCarIU dtoSaledCarIU) {
		
		
	   Optional<Customer> optCustomer  = customerRepository.findById(dtoSaledCarIU.getCustomerId());
	   if (optCustomer.isEmpty()) {
		   throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST , dtoSaledCarIU.getCustomerId().toString()));
	   }
	   
	   Optional<Car> optCar = carRepository.findById(dtoSaledCarIU.getCarId());
	   if (optCar.isEmpty()) {
		   throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST , dtoSaledCarIU.getCarId().toString()));
	   }
	   
	   BigDecimal customerUSDAmount = convertCustomerAmountToUsd(optCustomer.get());
	   
	   if (customerUSDAmount.compareTo(optCar.get().getPrice()) ==0 || customerUSDAmount.compareTo(optCar.get().getPrice()) >0) {
		   return true;
	   }
	   return false;
		
	}
	
	public BigDecimal remaningCustomerAmount (Customer customer , Car car) {
		BigDecimal customerUSDAmount = convertCustomerAmountToUsd(customer);
		BigDecimal remaingCustomerUSDAmount = customerUSDAmount.subtract(car.getPrice());
		
		CurrencyRatesResponse currencyRatesResponse = currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
		BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
		
		return remaingCustomerUSDAmount.multiply(usd);
	}
	
	
	
	public SaledCar createSaledCar (DtoSaledCarIU dtoSaledCarIU) {
		
		SaledCar saledCar = new SaledCar();
		saledCar.setCreateTime(new Date());
		
		saledCar.setCustomer(customerRepository.findById(dtoSaledCarIU.getCustomerId()).orElse(null));
		saledCar.setGallerist(galleristRepository.findById(dtoSaledCarIU.getGalleristId()).orElse(null));
		saledCar.setCar(carRepository.findById(dtoSaledCarIU.getCarId()).orElse(null));
		
		return saledCar;
		
	}
	
	public boolean checkCarStatus (Long carId) {
		Optional<Car> optCar = carRepository.findById(carId);
		
		if (optCar.isPresent() && optCar.get().getCarStatusType().name().equals(CarStatusType.SALED.name()) ) {
			
			
			return false;
		}
		return true;
		
	}
	
	@Transactional
	@Override
	public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU) {
		
		if (!checkAmount(dtoSaledCarIU)) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH , ""));
			
		}
		
		if (!checkCarStatus(dtoSaledCarIU.getCarId())) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_STATUS_IS_SALED , dtoSaledCarIU.getCarId().toString()));
		}
		
		SaledCar saveSaledCar = saledCarRepository.save(createSaledCar(dtoSaledCarIU));
		
		Car car = saveSaledCar.getCar();
		car.setCarStatusType(CarStatusType.SALED);
		
		carRepository.save(car);
		
		Customer customer = saveSaledCar.getCustomer();
		customer.getAccount().setAmount(remaningCustomerAmount(customer, car));
		customerRepository.save(customer);
		
		return toDto(saveSaledCar);
	}
	
	public DtoSaledCar toDto (SaledCar saledCar) {
		DtoSaledCar dtoSaledCar = new DtoSaledCar();
		DtoCar dtoCar = new DtoCar();
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoGallerist dtoGallerist = new DtoGallerist();
		
		BeanUtils.copyProperties(saledCar, dtoSaledCar);
		BeanUtils.copyProperties(saledCar.getCar(), dtoCar);
		BeanUtils.copyProperties(saledCar.getCustomer(), dtoCustomer);
		BeanUtils.copyProperties(saledCar.getGallerist(), dtoGallerist);
		
		dtoSaledCar.setCustomer(dtoCustomer);
		dtoSaledCar.setGallerist(dtoGallerist);
		dtoSaledCar.setCar(dtoCar);
		
		return dtoSaledCar;
	}

}

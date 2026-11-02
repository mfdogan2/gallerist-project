package com.furkandogan.service.ımpl;

import java.math.BigDecimal;
import java.util.UUID;
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
import com.furkandogan.enums.CurrencyType;
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
import org.springframework.context.ApplicationEventPublisher;
import com.furkandogan.event.CarSoldEvent;
import com.furkandogan.model.Gallerist;

@Service
public class SaledCarServiceImpl implements ISaledCarService{

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
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
	
	public boolean checkAmount(DtoSaledCarIU dtoSaledCarIU) {

	    Customer customer = customerRepository
	            .findById(dtoSaledCarIU.getCustomerId())
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            dtoSaledCarIU.getCustomerId().toString()
	                    )
	            ));

	    Car car = carRepository
	            .findById(dtoSaledCarIU.getCarId())
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            dtoSaledCarIU.getCarId().toString()
	                    )
	            ));

	    BigDecimal customerAmount = customer.getAccount().getAmount();
	    BigDecimal carPrice = car.getPrice();

	    if (customer.getAccount().getCurrencyType()
	            != car.getCurrencyType()) {

	        CurrencyRatesResponse response =
	                currencyRatesService.getCurrencyRates(
	                        DateUtils.getCurrentDate(new Date()),
	                        DateUtils.getCurrentDate(new Date())
	                );

	        BigDecimal usdRate =
	                new BigDecimal(response.getItems().get(0).getUsd());

	        if (car.getCurrencyType() == CurrencyType.USD) {
	            carPrice = carPrice.multiply(usdRate);
	        } else {
	            carPrice = carPrice.divide(
	                    usdRate, 2, RoundingMode.HALF_UP
	            );
	        }
	    }

	    return customerAmount.compareTo(carPrice) >= 0;
	}
	
	public BigDecimal remaningCustomerAmount(Customer customer, Car car) {

	    BigDecimal customerAmount = customer.getAccount().getAmount();
	    BigDecimal carPrice = car.getPrice();

	    if (customer.getAccount().getCurrencyType()
	            != car.getCurrencyType()) {

	        CurrencyRatesResponse response =
	                currencyRatesService.getCurrencyRates(
	                        DateUtils.getCurrentDate(new Date()),
	                        DateUtils.getCurrentDate(new Date())
	                );

	        BigDecimal usdRate =
	                new BigDecimal(response.getItems().get(0).getUsd());

	        if (car.getCurrencyType() == CurrencyType.USD) {
	            carPrice = carPrice.multiply(usdRate);
	        } else {
	            carPrice = carPrice.divide(
	                    usdRate, 2, RoundingMode.HALF_UP
	            );
	        }
	    }

	    return customerAmount.subtract(carPrice);
	}
	
	
	
	public SaledCar createSaledCar(DtoSaledCarIU dtoSaledCarIU) {

	    SaledCar saledCar = new SaledCar();
	    saledCar.setCreateTime(new Date());

	    Customer customer = customerRepository
	            .findById(dtoSaledCarIU.getCustomerId())
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            "Customer ID: " + dtoSaledCarIU.getCustomerId()
	                    )
	            ));

	    Gallerist gallerist = galleristRepository
	            .findById(dtoSaledCarIU.getGalleristId())
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            "Gallerist ID: " + dtoSaledCarIU.getGalleristId()
	                    )
	            ));

	    Car car = carRepository
	            .findById(dtoSaledCarIU.getCarId())
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            "Car ID: " + dtoSaledCarIU.getCarId()
	                    )
	            ));

	    saledCar.setCustomer(customer);
	    saledCar.setGallerist(gallerist);
	    saledCar.setCar(car);

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

	    // 1. Arabayı kilitleyerek getir
	    Car lockedCar = carRepository
	            .findByIdForUpdate(dtoSaledCarIU.getCarId())
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            dtoSaledCarIU.getCarId().toString()
	                    )
	            ));

	    // 2. Kilit alındıktan sonra durumunu kontrol et
	    if (lockedCar.getCarStatusType() != CarStatusType.SALABLE) {
	        throw new BaseException(
	                new ErrorMessage(
	                        MessageType.CAR_STATUS_IS_SALED,
	                        dtoSaledCarIU.getCarId().toString()
	                )
	        );
	    }

	    // 3. Müşterinin bakiyesini kontrol et
	    if (!checkAmount(dtoSaledCarIU)) {
	        throw new BaseException(
	                new ErrorMessage(
	                        MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH,
	                        ""
	                )
	        );
	    }

	    // 4. Satış kaydını oluştur
	    SaledCar saveSaledCar =
	            saledCarRepository.save(createSaledCar(dtoSaledCarIU));

	    // 5. Arabayı satıldı olarak işaretle
	    lockedCar.setCarStatusType(CarStatusType.SALED);
	    carRepository.save(lockedCar);

	    // 6. Müşterinin bakiyesini güncelle
	    Customer customer = saveSaledCar.getCustomer();

	    customer.getAccount().setAmount(
	            remaningCustomerAmount(customer, lockedCar)
	    );

	    customerRepository.save(customer);

	 // Satış olayını Spring içerisinde yayımla
	    CarSoldEvent event = new CarSoldEvent(
	    	    lockedCar.getId(),
	    	    customer.getId(),
	    	    "CAR_SOLD",
	    	    UUID.randomUUID().toString()
	    	);

	 eventPublisher.publishEvent(event);

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

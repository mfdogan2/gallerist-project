package com.furkandogan.service.ımpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.furkandogan.dto.DtoAccount;
import com.furkandogan.dto.DtoAccountIU;
import com.furkandogan.dto.DtoAddress;
import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoCustomerIU;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.model.Account;
import com.furkandogan.model.Address;
import com.furkandogan.model.Customer;
import com.furkandogan.repository.AccountRepository;
import com.furkandogan.repository.AddressRepository;
import com.furkandogan.repository.CustomerRepository;
import com.furkandogan.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	public Customer createCustomer (DtoCustomerIU dtoCustomerIU) {
		Optional<Address> optAddress = addressRepository.findById(dtoCustomerIU.getAddressId());
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoCustomerIU.getAddressId().toString()));
			
		}
		
		Optional<Account> optAccount = accountRepository.findById(dtoCustomerIU.getAccountId());
		if (optAccount.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoCustomerIU.getAccountId().toString() ));
		}
		
		
		Customer customer = new Customer();
		customer.setCreateTime(new Date());
		BeanUtils.copyProperties(dtoCustomerIU, customer);
		
		customer.setAddress(optAddress.get());
		customer.setAccount(optAccount.get());
		return customer;
		
	}
	
	@Override
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
		
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();
		Customer savedCustomer = customerRepository.save(createCustomer(dtoCustomerIU));
		
		BeanUtils.copyProperties(savedCustomer, dtoCustomer);
		BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
		BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);
		
		dtoCustomer.setAccount(dtoAccount);
		dtoCustomer.setAddress(dtoAddress);
		return dtoCustomer;
	}

}

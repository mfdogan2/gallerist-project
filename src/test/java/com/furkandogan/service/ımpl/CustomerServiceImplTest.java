package com.furkandogan.service.ımpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.furkandogan.dto.DtoCustomer;
import com.furkandogan.dto.DtoCustomerIU;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.MessageType;
import com.furkandogan.model.Account;
import com.furkandogan.model.Address;
import com.furkandogan.model.Customer;
import com.furkandogan.repository.AccountRepository;
import com.furkandogan.repository.AddressRepository;
import com.furkandogan.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void saveCustomerTest() {

        DtoCustomerIU request = new DtoCustomerIU();
        request.setFirstName("Furkan");
        request.setLastName("Doğan");
        request.setTckn("12345678901");
        request.setAddressId(1L);
        request.setAccountId(1L);

        Address address = new Address();
        address.setId(1L);

        Account account = new Account();
        account.setId(1L);

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DtoCustomer result = customerService.saveCustomer(request);

        assertEquals("Furkan", result.getFirstName());
        assertEquals("Doğan", result.getLastName());
        assertEquals(1L, result.getAddress().getId());
        assertEquals(1L, result.getAccount().getId());

        verify(addressRepository).findById(1L);
        verify(accountRepository).findById(1L);

        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }


    @Test
    void saveCustomerAddressNotFoundTest() {

        DtoCustomerIU request = new DtoCustomerIU();
        request.setAddressId(999L);
        request.setAccountId(1L);

        when(addressRepository.findById(999L))
                .thenReturn(Optional.empty());

        BaseException exception = assertThrows(
                BaseException.class,
                () -> customerService.saveCustomer(request)
        );

        assertEquals(
                MessageType.NO_RECORD_EXIST,
                exception.getMessageType()
        );

        verify(addressRepository).findById(999L);

        verify(accountRepository, never())
                .findById(anyLong());

        verify(customerRepository, never())
                .save(any(Customer.class));
    }


    @Test
    void saveCustomerAccountNotFoundTest() {

        DtoCustomerIU request = new DtoCustomerIU();
        request.setAddressId(1L);
        request.setAccountId(999L);

        Address address = new Address();
        address.setId(1L);

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        when(accountRepository.findById(999L))
                .thenReturn(Optional.empty());

        BaseException exception = assertThrows(
                BaseException.class,
                () -> customerService.saveCustomer(request)
        );

        assertEquals(
                MessageType.NO_RECORD_EXIST,
                exception.getMessageType()
        );

        verify(addressRepository).findById(1L);
        verify(accountRepository).findById(999L);

        verify(customerRepository, never())
                .save(any(Customer.class));
    }
}
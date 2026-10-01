package com.furkandogan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furkandogan.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

}

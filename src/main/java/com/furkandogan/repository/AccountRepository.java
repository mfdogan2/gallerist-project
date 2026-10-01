package com.furkandogan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furkandogan.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

}

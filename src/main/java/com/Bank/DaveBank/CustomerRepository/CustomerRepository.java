package com.Bank.DaveBank.CustomerRepository;

import com.Bank.DaveBank.CustomerEntity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {


}
package com.Bank.DaveBank.CustomerRepository;

import com.Bank.DaveBank.CustomerEntity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

   Optional<Customer> findByEmail(String email);
}
package com.Bank.DaveBank.CustomerRepository;

import com.Bank.DaveBank.CustomerEntity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

   Optional<Customer> findByEmail(String email);

   Optional<Customer> findByAccountNumber(String accountNumber);

   UserDetails findUserByEmail(String email);
}
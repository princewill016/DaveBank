package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
   BankResponse createAccount( CustomerDto customerDto);

}
package com.Bank.DaveBank.CustomerController;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerService.CustomerService;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping (path = "/BankOperations")
public class CustomerController {

   private CustomerService customerService;

   @PostMapping("/createAccount")
   public BankResponse createAccount(CustomerDto customerDto){
      return customerService.createAccount(customerDto);
   }
}
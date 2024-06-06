package com.Bank.DaveBank.CustomerController;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerService.CustomerService;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "/BankOperations")
public class CustomerController {
@Autowired
   private CustomerService customerService;

   @PostMapping("/createAccount")
   public BankResponse createAccount( @RequestBody CustomerDto customerDto){
      return customerService.createAccount(customerDto);
   }

   @GetMapping (path = "{accountNumber}")
   public BankResponse getAccDetail(@PathVariable("accountNumber") String accountNumber ){
      return customerService.getAccDetail(accountNumber);
   }




}
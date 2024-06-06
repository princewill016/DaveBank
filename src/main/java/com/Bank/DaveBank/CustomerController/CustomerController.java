package com.Bank.DaveBank.CustomerController;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.CreditDebit;
import com.Bank.DaveBank.CustomerService.CustomerService;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (path = "/BankOperations")
public class CustomerController {
@Autowired
   private CustomerService customerService;

   @PostMapping("/createAccount")
   public BankResponse createAccount( @RequestBody CustomerDto customerDto){
      return customerService.createAccount(customerDto);
   }
   @PostMapping("/creditAcc")
   public String creditAcc( @RequestBody CreditDebit creditDebit){
      return customerService.creditAcc(creditDebit);
   }
   @PostMapping("/debitAcc")
   public String debitAcc( @RequestBody CreditDebit creditDebit){
      return customerService.debitAcc(creditDebit);
   }  @PostMapping("/transfer")
   public String transfer( @RequestBody CreditDebit creditDebit){
      return customerService.transfer(creditDebit);
   }
   @GetMapping (path = "{accountName}")
   public String  getAccName(@PathVariable("accountName") String accountNumber ){
      return customerService.getAccName(accountNumber);
   }
   @GetMapping (path = "/bal/{accountBalance}")
   public String  getAccBal(@PathVariable("accountBalance") String accountNumber ){
      return customerService.getAccBal(accountNumber);
   }




}
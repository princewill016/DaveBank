package com.Bank.DaveBank.CustomerController;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.CreditDebit;
import com.Bank.DaveBank.CustomerService.CustomerService;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/BankOperations")
public class CustomerController {
   @Autowired
   private CustomerService customerService;

   @PostMapping("/createAccount")
   public BankResponse createAccount(@RequestBody CustomerDto customerDto) {
      return customerService.createAccount(customerDto);
   }

   @PostMapping("/creditAcc")
   public String creditAcc(@RequestBody CreditDebit creditDebit) {
      return customerService.creditAcc(creditDebit);
   }

   @PostMapping("/debitAcc")
   public String debitAcc(@RequestBody CreditDebit creditDebit) {
      return customerService.debitAcc(creditDebit);
   }

   @PostMapping("/transfer")
   public String transfer(@RequestBody CreditDebit creditDebit) {
      return customerService.transfer(creditDebit);
   }

   @GetMapping(path = "{accountName}")
   public String getAccName(@PathVariable("accountName") String accountNumber) {
      return customerService.getAccName(accountNumber);
   }

   @GetMapping(path = "/bal/{accountBalance}")
   public String getAccBal(@PathVariable("accountBalance") String accountNumber) {
      return customerService.getAccBal(accountNumber);
   }

   @DeleteMapping(path = "/deleteAccount/{id}")
   public String deleteAcc(@PathVariable String id) {
      return customerService.deleteAcc(id);
   }

   @PutMapping(path = "/editAccount/{id}")
   public String editAccount(@PathVariable String id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String otherName, @RequestParam(required = false) String email, @RequestParam(required = false) String address, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String stateOfOrigin, @RequestParam(required = false) String nextOfKin, @RequestParam(required = false) String nextOfKinPhone) {
      return customerService.updateAccount(id, firstName, lastName, otherName, email, address, phoneNumber, stateOfOrigin, nextOfKin, nextOfKinPhone);
   }

}
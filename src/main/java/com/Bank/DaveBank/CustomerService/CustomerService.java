package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.CreditDebit;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
   BankResponse createAccount(CustomerDto customerDto);

   String getAccName(String accountNumber);

   String getAccBal(String accountNumber);

   String creditAcc(CreditDebit creditDebit);

   String debitAcc(CreditDebit creditDebit);

   String transfer(CreditDebit creditDebit);

   String deleteAcc(String id);

   String updateAccount(String id, String firstName, String lastName, String otherName, String email, String address, String phoneNumber, String stateOfOrigin, String nextOfKin, String nextOfKinPhone);
}
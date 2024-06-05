package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import com.Bank.DaveBank.CustomerUtils.AccountInfo;
import com.Bank.DaveBank.CustomerUtils.AccountUtils;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerServiceImpl implements  CustomerService{
   @Autowired
   private AccountUtils accountUtils;

   @Autowired
   private CustomerRepository customerRepository;

   @Override
   public BankResponse createAccount(CustomerDto customerDto) {

      Customer newCustomer = Customer
            .builder()
            .firstName(customerDto.getFirstName())
            .lastName(customerDto.getLastName())
            .otherName(customerDto.getOtherName())
            .accountBalance(BigDecimal.valueOf(0.00))
            .accountNumber(AccountUtils.generateAccountNumber())
            .accountStatus("Active")
            .address(customerDto.getAddress())
            .email(customerDto.getEmail())
            .gender(customerDto.getGender())
            .nextOfKin(customerDto.getNextOfKin())
            .phoneNumber(customerDto.getPhoneNumber())
            .stateOfOrigin(customerDto.getStateOfOrigin())
            .nextOfKinPhone(customerDto.getNextOfKinPhone())
            .build();
      customerRepository.save(newCustomer);

      String accountName = customerDto.getFirstName() + " " + customerDto.getLastName() + " " + customerDto.getOtherName();
      String accountNumber = AccountUtils.generateAccountNumber();
      AccountInfo accountInfo = AccountInfo.builder()
            .accountName(accountName)
            .accountNumber(accountNumber)
            .accountBalance("0.00")
            .build();
      return BankResponse.builder()
            .responseCode("001")
            .responseMessage("Account Created Successfully")
            .accountInfo(accountInfo)
            .build();
   }
}
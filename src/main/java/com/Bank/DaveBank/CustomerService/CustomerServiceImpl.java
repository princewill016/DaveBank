package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import com.Bank.DaveBank.CustomerUtils.AccountInfo;
import com.Bank.DaveBank.CustomerUtils.AccountUtils;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceImpl implements  CustomerService{
   private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
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
            .accountBalance(BigDecimal.ZERO)
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
    Customer savedCustomer  =   customerRepository.save(newCustomer);
    ;
      logger.info("Creating account for: {}", customerDto);
      return BankResponse.builder()
            .responseCode(AccountUtils.RESPONSE)
            .responseMessage(AccountUtils.RESPONSE_MESSAGE)
            .accountInfo(AccountInfo.builder()
                  .accountName( savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getOtherName())
                  .accountNumber(savedCustomer.getAccountNumber())
                  .accountBalance(savedCustomer.getAccountBalance())
                  .build())
            .build();

   }
}
package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import com.Bank.DaveBank.CustomerUtils.AccountInfo;
import com.Bank.DaveBank.CustomerUtils.AccountUtils;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import com.Bank.DaveBank.CustomerUtils.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceImpl implements  CustomerService{
   private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
   @Autowired
   private CustomerRepository customerRepository;

   @Autowired
   private EmailService emailService;


   @Override
   public BankResponse createAccount(CustomerDto customerDto) {
      Optional<Customer> accountExist = customerRepository.findByEmail(customerDto.getEmail());

      if(accountExist.isPresent()){
         return BankResponse.builder()
               .responseCode("405")
               .responseMessage("Account already exist")
               .accountInfo(null)
               .build();
      }

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
    String cusDetail =  savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getOtherName() + "\n" + savedCustomer.getAccountNumber()+  "\n" +  savedCustomer.getId() +  "\n " + savedCustomer.getCreatedAt();
     emailService.sendEmail(savedCustomer.getEmail(),"Bank Account Created Successfully", " Welcome to Our banking Platform...Thank You for Banking with us find your account details below" +  "\n " + cusDetail );
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

   @Override
   public String getAccName(String accountNumber) {
      Optional<Customer> accDetailOpt = customerRepository.findByAccountNumber(accountNumber);
      if (accDetailOpt.isEmpty()) {
         return "Account Not found";
      }
      Customer accDetail = accDetailOpt.get();
      return   accDetail.getFirstName() + " " + accDetail.getLastName() + " " + accDetail.getOtherName();
   }   @Override
   public String getAccBal(String accountNumber) {
      Optional<Customer> accDetailOpt = customerRepository.findByAccountNumber(accountNumber);
      if (accDetailOpt.isEmpty()) {
         return "Account Not found";
      }
      Customer accDetail = accDetailOpt.get();
      return  ( accDetail.getAccountBalance()).toString();
   }



}
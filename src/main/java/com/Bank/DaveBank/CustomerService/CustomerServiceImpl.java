package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CreditDebit;
import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import com.Bank.DaveBank.CustomerUtils.AccountInfo;
import com.Bank.DaveBank.CustomerUtils.AccountUtils;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import com.Bank.DaveBank.CustomerUtils.EmailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceImpl implements CustomerService {
   @Autowired
   private CustomerRepository customerRepository;

   @Autowired
   private EmailService emailService;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public BankResponse createAccount(CustomerDto customerDto) {
      Optional<Customer> accountExist = customerRepository.findByEmail(customerDto.getEmail());

      if(accountExist.isPresent()) {
         return BankResponse.builder().responseCode("405").responseMessage("Account already exist").accountInfo(null).build();
      }

      Customer newCustomer = Customer.builder().firstName(customerDto.getFirstName()).lastName(customerDto.getLastName()).otherName(customerDto.getOtherName()).accountBalance(BigDecimal.ZERO).accountNumber(AccountUtils.generateAccountNumber()).accountStatus("Active").address(customerDto.getAddress()).email(customerDto.getEmail()).gender(customerDto.getGender()).nextOfKin(customerDto.getNextOfKin()).phoneNumber(customerDto.getPhoneNumber()).stateOfOrigin(customerDto.getStateOfOrigin()).password(passwordEncoder.encode(customerDto.getPassword())).nextOfKinPhone(customerDto.getNextOfKinPhone()).build();
      Customer savedCustomer = customerRepository.save(newCustomer);
      String cusDetail = savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getOtherName() + "\n" + savedCustomer.getAccountNumber() + "\n" + savedCustomer.getId() + "\n " + savedCustomer.getCreatedAt();
      emailService.sendEmail(savedCustomer.getEmail(), "Bank Account Created Successfully", " Welcome to Our banking Platform...Thank You for Banking with us find your account details below" + "\n " + cusDetail);
      return BankResponse.builder().responseCode(AccountUtils.RESPONSE).responseMessage(AccountUtils.RESPONSE_MESSAGE).accountInfo(AccountInfo.builder().accountName(savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getOtherName()).accountNumber(savedCustomer.getAccountNumber()).accountID(savedCustomer.getId()).accountBalance(savedCustomer.getAccountBalance()).build()).build();

   }

   @Override
   public String getAccName(String accountNumber) {
      Optional<Customer> accDetailOpt = customerRepository.findByAccountNumber(accountNumber);
      if(accDetailOpt.isEmpty()) {
         return "Account Not found";
      }
      Customer accDetail = accDetailOpt.get();
      return accDetail.getFirstName() + " " + accDetail.getLastName() + " " + accDetail.getOtherName();
   }

   @Override
   public String getAccBal(String accountNumber) {
      Optional<Customer> accDetailOpt = customerRepository.findByAccountNumber(accountNumber);
      if(accDetailOpt.isEmpty()) {
         return "Account Not found";
      }
      Customer accDetail = accDetailOpt.get();
      return (accDetail.getAccountBalance()).toString();
   }

   @Override
   public String creditAcc(CreditDebit creditDebit) {
      Optional<Customer> getAccToCreditOpt = customerRepository.findByAccountNumber(creditDebit.getAccountToCredit());
      if(getAccToCreditOpt.isEmpty()) {
         return "Account not found";
      }
      String amountStr = creditDebit.getAmount();
      if(amountStr == null || amountStr.trim().isEmpty()) {
         return "Amount to credit cannot be null or empty";
      }
      Customer getAccToCredit = getAccToCreditOpt.get();
      BigDecimal currentBalance = getAccToCredit.getAccountBalance();
      BigDecimal amountToCredit = new BigDecimal(creditDebit.getAmount());
      BigDecimal newBalance = currentBalance.add(amountToCredit);
      getAccToCredit.setAccountBalance(newBalance);
      customerRepository.save(getAccToCredit);
      return "Account credited successfully. New balance: " + newBalance;
   }

   @Override
   public String debitAcc(CreditDebit creditDebit) {
      Optional<Customer> getAccToDebitOpt = customerRepository.findByAccountNumber(creditDebit.getAccountToDebit());
      if(getAccToDebitOpt.isEmpty()) {
         return "Account not found";
      }
      String amountStr = creditDebit.getAmount();
      if(amountStr == null || amountStr.trim().isEmpty()) {
         return "Amount to debit cannot be null or empty";
      }
      Customer getAccToDebit = getAccToDebitOpt.get();
      BigDecimal currentBalance = getAccToDebit.getAccountBalance();
      BigDecimal amountToDebit = new BigDecimal(creditDebit.getAmount());
      if(currentBalance.compareTo(amountToDebit) < 0) {
         return "insufficient Balance for this transaction";
      }
      BigDecimal newBalance = currentBalance.subtract(amountToDebit);
      getAccToDebit.setAccountBalance(newBalance);
      customerRepository.save(getAccToDebit);
      return "Account debited successfully. New balance: " + newBalance;
   }

   @Override
   public String transfer(CreditDebit creditDebit) {
      Optional<Customer> getAccToDebitOpt = customerRepository.findByAccountNumber(creditDebit.getAccountToDebit());
      Optional<Customer> getAccToCreditOpt = customerRepository.findByAccountNumber(creditDebit.getAccountToCredit());
      if(getAccToDebitOpt.isEmpty() || getAccToCreditOpt.isEmpty()) {
         return "Account not found";
      }
      String amountStr = creditDebit.getAmount();
      if(amountStr == null || amountStr.trim().isEmpty()) {
         return "Amount to transfer cannot be null or empty";
      }
      BigDecimal amount = new BigDecimal(creditDebit.getAmount());
      Customer getAccToCredit = getAccToCreditOpt.get();
      Customer getAccToDebit = getAccToDebitOpt.get();
      if(getAccToDebit.getAccountBalance().compareTo(amount) < 0) {
         return "Insufficient balance for this Transaction";
      }
      getAccToCredit.setAccountBalance(getAccToCredit.getAccountBalance().add(amount));
      getAccToDebit.setAccountBalance(getAccToDebit.getAccountBalance().subtract(amount));
      customerRepository.save(getAccToCredit);
      customerRepository.save(getAccToDebit);
      return "Transfer successful. Your  current account balance: " + "N" + getAccToDebit.getAccountBalance();
   }

   @Override
   public String deleteAcc(String id) {
      Optional<Customer> accToDel = customerRepository.findById(id);
      if(accToDel.isEmpty()) {
         return "There is no Account with that ID";
      }
      customerRepository.deleteById(id);
      return "account deleted successfully";
   }

   @Override
   @Transactional
   public String updateAccount(String id, String firstName, String lastName, String otherName, String email, String address, String phoneNumber, String stateOfOrigin, String nextOfKin, String nextOfKinPhone) {
      Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("Your ID is Incorrect"));
      if(firstName != null && ! firstName.isEmpty() && ! Objects.equals(customer.getFirstName(), firstName)) {
         customer.setFirstName(firstName);
      }
      if(lastName != null && ! lastName.isEmpty() && ! Objects.equals(customer.getLastName(), lastName)) {
         customer.setLastName(lastName);
      }
      if(otherName != null && ! otherName.isEmpty() && ! Objects.equals(customer.getOtherName(), otherName)) {
         customer.setFirstName(otherName);
      }
      if(email != null && ! email.isEmpty() && ! Objects.equals(customer.getEmail(), email)) {
         customer.setEmail(email);
      }
      if(address != null && ! address.isEmpty() && ! Objects.equals(customer.getAddress(), address)) {
         customer.setAddress(address);
      }
      if(phoneNumber != null && ! phoneNumber.isEmpty() && ! Objects.equals(customer.getPhoneNumber(), phoneNumber)) {
         customer.setPhoneNumber(phoneNumber);
      }
      if(stateOfOrigin != null && ! stateOfOrigin.isEmpty() && ! Objects.equals(customer.getStateOfOrigin(), stateOfOrigin)) {
         customer.setStateOfOrigin(stateOfOrigin);
      }
      if(nextOfKinPhone != null && ! nextOfKinPhone.isEmpty() && ! Objects.equals(customer.getNextOfKinPhone(), nextOfKinPhone)) {
         customer.setNextOfKinPhone(nextOfKinPhone);
      }
      if(nextOfKinPhone != null && ! nextOfKinPhone.isEmpty() && ! Objects.equals(customer.getNextOfKinPhone(), nextOfKinPhone)) {
         customer.setNextOfKinPhone(nextOfKinPhone);
      }
      customerRepository.save(customer);
      emailService.sendEmail(customer.getEmail(), "Update successful", "You have successfully updated your bank Account!");
      return "Update successful\", \"You have successfully updated your bank Account!";
   }
}
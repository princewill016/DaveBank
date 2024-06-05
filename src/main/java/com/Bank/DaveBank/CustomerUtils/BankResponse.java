package com.Bank.DaveBank.CustomerUtils;

import com.Bank.DaveBank.CustomerEntity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankResponse {

   private Customer customer;

public String accountName= customer.getFirstName() + " " + customer.getLastName() + " " + customer.getOtherName();
public BigDecimal accountBalance= customer.getAccountBalance();

private AccountUtils accountUtils;
}
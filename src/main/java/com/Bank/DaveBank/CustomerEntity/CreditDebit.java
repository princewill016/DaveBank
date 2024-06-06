package com.Bank.DaveBank.CustomerEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDebit {
  private String amount;
   private String accountToDebit;
  private   String accountToCredit;
}
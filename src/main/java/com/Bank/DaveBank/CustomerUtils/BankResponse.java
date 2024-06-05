package com.Bank.DaveBank.CustomerUtils;

import com.Bank.DaveBank.CustomerEntity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankResponse {

   private  String responseCode;
   private   String responseMessage;
   private AccountInfo accountInfo;
}
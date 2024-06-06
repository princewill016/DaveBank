package com.Bank.DaveBank.CustomerUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class AccountInfo {
   private String accountName;
   private String accountNumber;
   private String accountID;
   private BigDecimal accountBalance;

}
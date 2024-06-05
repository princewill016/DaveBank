package com.Bank.DaveBank.CustomerDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
   private String firstName;
   private String lastName;
   private String otherName;
   private String gender;
   private String accountBalance;
   private String address;
   private String phoneNumber;
   private String accountStatus;
   private String stateOfOrigin;
   private String accountNumber;
   private String nextOfKin;
   private String nextOfKinPhone;
}
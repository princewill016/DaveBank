package com.Bank.DaveBank.CustomerDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
   private String firstName;
   private String lastName;
   private String otherName;
   private String gender;
   private String email;
   private String address;
   private String phoneNumber;
   private String stateOfOrigin;
   private String nextOfKin;
   private String nextOfKinPhone;
}
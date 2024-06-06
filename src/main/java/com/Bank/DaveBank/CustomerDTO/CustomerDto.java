package com.Bank.DaveBank.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

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
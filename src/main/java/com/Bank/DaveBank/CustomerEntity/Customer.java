package com.Bank.DaveBank.CustomerEntity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Customer details")
public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private String id;
   private String firstName;
   private String lastName;
   private String otherName;
   private String gender;
   private String email;
   private String password;
   private BigDecimal accountBalance;
   private String address;
   private String phoneNumber;
   private String accountStatus;
   private String stateOfOrigin;
   private String accountNumber;
   private String nextOfKin;
   private String nextOfKinPhone;
   @CreationTimestamp
   private LocalDateTime createdAt;
   @UpdateTimestamp
   private LocalDateTime modifiedAt;

}
package com.Bank.DaveBank.CustomerEntity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Customer details")
public class Customer implements UserDetails {
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

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of();
   }

   @Override
   public String getUsername() {
      return "";
   }
}
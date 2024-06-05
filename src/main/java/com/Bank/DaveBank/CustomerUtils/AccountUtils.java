package com.Bank.DaveBank.CustomerUtils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {
   public static final String STATUS_CODE= "001";
   public static final String STATUS_MESSAGE= "Account created successfully";


   public static String generateAccountNumber() {
      int currentYear = Year.now().getValue();
      Random random = new Random();
      int randomNumber = random.nextInt(1_000_000);
      return String.format("%04d%06d", currentYear, randomNumber);
   }


}
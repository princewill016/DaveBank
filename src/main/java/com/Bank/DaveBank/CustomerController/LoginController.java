package com.Bank.DaveBank.CustomerController;

import com.Bank.DaveBank.BankSecurity.Login.Jwt;
import com.Bank.DaveBank.BankSecurity.Login.LoginDAO;
import com.Bank.DaveBank.BankSecurity.SecurityConfig.JwtUtil;
import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private JwtUtil jwtUtils;
   @Autowired
   private CustomerRepository customerRepository;

   @PostMapping("/BankOperations/login")
   public Jwt login(@RequestBody LoginDAO loginDAO) throws Exception {
      try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDAO.getEmail(), loginDAO.getPassword()));
      } catch(BadCredentialsException e) {
         throw new Exception("incorrect username or password", e);
      }
      final UserDetails user = customerRepository.findUserByEmail(loginDAO.getEmail());

      final String jwt = jwtUtils.generateToken(user);

      return new Jwt(jwt);
   }
}
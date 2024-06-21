package com.Bank.DaveBank.BankSecurity.SecurityConfig;

import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
   @Autowired
   private CustomerRepository customerRepository;

   @Autowired
   private JwtAuthFilter jwtAuthFilter;

   @Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   AuthenticationProvider authenticationProvider() {
      final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailsService());
      authenticationProvider.setPasswordEncoder(passwordEncoder());
      return authenticationProvider;

   }

   @Bean
   UserDetailsService userDetailsService() {
      return new UserDetailsService() {
         @Override
         public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return customerRepository.findUserByEmail(email);
         }
      };
   }

   @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
   }

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      http.csrf(csrf -> csrf.disable()).authorizeRequests(requests -> {
         try {
            requests.requestMatchers("BankOperations/createAccount", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/webjars/**", "/swagger-ui.html", "/swagger-ui/**", "BankOperations/login", "/actuator", "/actuator/*").permitAll().anyRequest().authenticated().and().sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
         } catch(Exception e) {
            e.printStackTrace();
         }
      });
      return http.build();

   }
}
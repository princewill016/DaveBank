package com.Bank.DaveBank.BankSecurity.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

   @Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      http.csrf(csrf -> csrf.disable()).authorizeRequests(requests -> {
         try {
            requests.requestMatchers("BankOperations/createAccount", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/webjars/**", "/swagger-ui.html", "/swagger-ui/**").permitAll().anyRequest().authenticated().and().sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                  .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
         } catch(Exception e) {
            e.printStackTrace();
         }
      });
      return http.build();

   }
}
package com.Bank.DaveBank.BankSecurity.SecurityConfig;

import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
   @Autowired
   private JwtUtil jwtUtil;
   @Autowired
   private CustomerRepository customerRepository;

   @SuppressWarnings("null")
   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      final String authHeader = request.getHeader("AUTHORIZATION");

      String jwt = null;
      String email = null;
      if(authHeader == null || ! authHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }

      if(authHeader != null && authHeader.startsWith("Bearer ")) {
         jwt = authHeader.substring(7);
         email = jwtUtil.extractUsername(jwt);
      }

      if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         UserDetails userDetails = customerRepository.findUserByEmail(email);
         if(jwtUtil.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
         } else {
            // Log token validation failure
            logger.warn("Invalid JWT token");
         }
      }

      filterChain.doFilter(request, response);
   }
}
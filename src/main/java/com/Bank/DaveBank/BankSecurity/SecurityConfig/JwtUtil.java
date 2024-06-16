package com.Bank.DaveBank.BankSecurity.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
   private static final byte[] KEY_BYTES = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
   private static final String SECRET_KEY = java.util.Base64.getEncoder().encodeToString(KEY_BYTES);

   public boolean hasClaim(String token, String claimName) {
      final Claims claims = extractAllClaims(token);
      return claims.get(claimName) != null;
   }

   public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
   }

   public Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
   }

   public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
      final Claims claims = extractAllClaims(token);
      return claimResolver.apply(claims);
   }

   @SuppressWarnings("deprecation")
   private Claims extractAllClaims(String token) {
      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
   }

   public String generateToken(UserDetails userDetails) {
      Map<String, Object> claims = new HashMap<>();
      return createToken(claims, userDetails.getUsername());
   }

   private Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
   }

   @SuppressWarnings("deprecation")
   private String createToken(Map<String, Object> claims, String subject) {
      return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
   }

   public Boolean isTokenValid(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      final boolean isUsernameValid = username.equals(userDetails.getUsername());
      final boolean isTokenExpired = isTokenExpired(token);
      return isUsernameValid && ! isTokenExpired;
   }
}
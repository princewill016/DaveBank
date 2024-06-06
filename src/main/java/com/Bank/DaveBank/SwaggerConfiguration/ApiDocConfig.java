package com.Bank.DaveBank.SwaggerConfiguration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfig {
   private static final String SECURITY_SCHEME_NAME = "Bearer Token";

   @Bean
   OpenAPI openAPI() {
      return new OpenAPI().info(new Info().title("Banking Application").description("To mimic traditional banking system").version("1.0.0").license(new License().name("Apache 2.0")));
   }
}

//.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME)).components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme().name(SECURITY_SCHEME_NAME).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
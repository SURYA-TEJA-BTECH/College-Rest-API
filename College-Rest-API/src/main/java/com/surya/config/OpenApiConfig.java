package com.surya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.surya.ApplicationProperties;
import com.surya.ApplicationProperties.OpenApiProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
     OpenAPI openApi(ApplicationProperties properties) {
        // Retrieve OpenAPI properties
        OpenApiProperties openApiProperties = properties.openApi();

        // Create Contact Information
        Contact contact = new Contact()
                .name(openApiProperties.contact().name())
                .email(openApiProperties.contact().email());

        // Create API Info
        Info info = new Info()
                .title(openApiProperties.title())
                .description(openApiProperties.description())
                .version(openApiProperties.version())
                .contact(contact);

        // Add Security Requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        // Create and Return OpenAPI Object
        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement) // Apply authorization globally
                .components(createComponents());
    }

    private Components createComponents() {
        // Add Security Scheme to Components
        return new Components()
                .addSecuritySchemes("Bearer Authentication", createJwtTokenScheme());
    }

    private SecurityScheme createJwtTokenScheme() {
        // Define JWT Security Scheme
        return new SecurityScheme()
                .name("Authorization") // Header name
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}

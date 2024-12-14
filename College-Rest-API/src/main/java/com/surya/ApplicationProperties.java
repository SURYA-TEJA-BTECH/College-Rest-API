package com.surya;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.validation.constraints.Min;

@ConfigurationProperties(prefix = "student")
public record ApplicationProperties(

		@Min(1) @DefaultValue("10") int pageSize,

		JwtProperties jwt,

		OpenApiProperties openApi

)

{

	public record JwtProperties(

			@DefaultValue("college") String issuer, @DefaultValue("604800") Long expireInseconds,

			String secretKey

	) {

	}

	public record OpenApiProperties(

			@DefaultValue("Student Rest API") String title,
			@DefaultValue("Student Rest API Swagger Documentation") String description,
			@DefaultValue("v1.0.0") String version, Contact contact) {

		public record Contact(@DefaultValue("surya") String name, @DefaultValue("isurya@gmailcom") String email) {
		}
	}

}

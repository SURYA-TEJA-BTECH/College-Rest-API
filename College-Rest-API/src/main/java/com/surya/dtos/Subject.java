package com.surya.dtos;

import jakarta.validation.constraints.NotEmpty;

public record Subject(

		@NotEmpty(message = "subject is required") String name) {

}

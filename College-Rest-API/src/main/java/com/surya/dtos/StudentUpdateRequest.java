package com.surya.dtos;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StudentUpdateRequest(

		@NotNull(message = "id is required") Long id,

		@NotEmpty(message = "name is required") String name,

		@NotEmpty(message = "address is required") String address,

		@Valid @NotEmpty(message = "students should register atlest one min") Set<Subject> subjects

) {
}

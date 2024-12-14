package com.surya.dtos;

import java.util.Set;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record StudentRegisterationData(

		@NotEmpty(message = "name is required") String name,

		@NotEmpty(message = "address is required") String address,

		@Valid @NotEmpty(message = "students should register atlest one min") Set<Subject> subjects

) {

}

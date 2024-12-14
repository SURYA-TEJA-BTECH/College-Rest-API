package com.surya.dtos;

import java.util.Set;

public record StudentDto(

		Long id,

		String name,

		String address,

		Set<String> subjects

) {

}

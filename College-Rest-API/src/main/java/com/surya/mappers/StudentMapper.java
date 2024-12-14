package com.surya.mappers;

import java.util.stream.Collectors;

import com.surya.dtos.StudentDto;
import com.surya.dtos.StudentRegisterationData;
import com.surya.entitys.StudentEntity;
import com.surya.entitys.SubjectEntity;
import com.surya.enums.StudentStatus;

public class StudentMapper {

	public static StudentDto toStudentDto(StudentEntity studentEntity) {
		return new StudentDto(studentEntity.getId(), studentEntity.getName(), studentEntity.getAddress(),
				studentEntity.getSubjects().stream().map(SubjectEntity::getName).collect(Collectors.toSet()));
	}

	

	public static StudentEntity toStudentEntity(StudentRegisterationData request) {

		StudentEntity studentEntity = new StudentEntity();

		studentEntity.setName(request.name());
		studentEntity.setAddress(request.address());

		studentEntity.setSubjects(request.subjects().stream().map(subject -> SubjectMapper.getSubjectEntity(subject))
				.collect(Collectors.toSet()));

		studentEntity.setStatus(StudentStatus.ACTIVE);

		return studentEntity;
	}

}

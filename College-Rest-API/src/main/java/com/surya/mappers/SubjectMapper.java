package com.surya.mappers;

import com.surya.dtos.Subject;
import com.surya.entitys.SubjectEntity;

public class SubjectMapper {

	public static SubjectEntity getSubjectEntity(Subject subject) {

		return new SubjectEntity(subject.name());
	}

}

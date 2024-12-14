package com.surya.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.surya.ApplicationProperties;
import com.surya.dtos.StudentDto;
import com.surya.dtos.StudentRegisterationData;
import com.surya.dtos.StudentUpdateRequest;
import com.surya.entitys.StudentEntity;
import com.surya.enums.StudentStatus;
import com.surya.exceptions.StudentNotFoundException;
import com.surya.mappers.StudentMapper;
import com.surya.mappers.SubjectMapper;
import com.surya.repos.IStudentRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService {

	private final IStudentRepo iStudentRepo;

	private final ApplicationProperties applicationProperties;

	@Override
	public StudentDto findStudentById(Long id) {

		return iStudentRepo.findById(id).map(StudentMapper::toStudentDto)
				.orElseThrow(() -> StudentNotFoundException.forId(id));

	}

	@Override
	public Long registerStudent(StudentRegisterationData studentRegisterationData) {

		return iStudentRepo.save(StudentMapper.toStudentEntity(studentRegisterationData)).getId();
	}

	@Override
	public StudentDto updateStudentData(StudentUpdateRequest request) {

		StudentEntity student = getStudent(request.id());

		//set values to student entity

		student.setName(request.name());

		student.setAddress(request.address());

		student.setSubjects(request.subjects().stream().map(SubjectMapper::getSubjectEntity)
				.collect(Collectors.toSet()));

		return StudentMapper.toStudentDto(iStudentRepo.save(student));
	}

	private StudentEntity getStudent(Long id) {
		return iStudentRepo.findById(id).orElseThrow(() -> StudentNotFoundException.forId(id));
	}

	@Override
	public void updateStudentAddress(Long id, String address) {
		StudentEntity student = getStudent(id);
		student.setAddress(address);
		iStudentRepo.save(student);
	}

	@Override
	public List<StudentDto> findAllStudents(Integer pageNo) {

		Sort sort = Sort.by(Direction.ASC, "name");

		pageNo = pageNo <= 1 ? 0 : pageNo - 1;

		Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);

		return iStudentRepo.findAll(pageable).map(StudentMapper::toStudentDto).toList();
	}

	@Override
	public void deleteStudent(Long id) {

		StudentEntity student = getStudent(id);
		student.setStatus(StudentStatus.INACTIVE);
		iStudentRepo.save(student);

	}

}

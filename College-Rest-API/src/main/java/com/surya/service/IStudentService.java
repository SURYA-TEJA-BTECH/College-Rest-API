package com.surya.service;

import java.util.List;

import com.surya.dtos.StudentDto;
import com.surya.dtos.StudentRegisterationData;
import com.surya.dtos.StudentUpdateRequest;

public interface IStudentService {

	public StudentDto findStudentById(Long id);

	public Long registerStudent(StudentRegisterationData studentRegisterationData);

	public StudentDto updateStudentData(StudentUpdateRequest studentUpdateRequest);

	public void updateStudentAddress(Long id, String address);

	public List<StudentDto> findAllStudents(Integer pageNo);
	
	public void deleteStudent(Long id);

}

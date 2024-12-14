package com.surya.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.surya.dtos.StudentDto;
import com.surya.dtos.StudentRegisterationData;
import com.surya.dtos.StudentUpdateRequest;
import com.surya.service.IStudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentOperationsController {

	private final IStudentService studentService;

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')") // Only Admin can access this method
	public ResponseEntity<List<StudentDto>> getAllStudents(@RequestParam(defaultValue = "1") Integer pageNo) {
		List<StudentDto> students = studentService.findAllStudents(pageNo);
		return ResponseEntity.ok(students);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Only Admin can access this method
	public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
		StudentDto student = studentService.findStudentById(id);
		return ResponseEntity.ok(student);
	}

	@PostMapping("/")
	@PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')") // Both Admin and Student can register
	public ResponseEntity<Long> registerStudent(@RequestBody @Valid StudentRegisterationData studentRegisterationData) {
		Long studentId = studentService.registerStudent(studentRegisterationData);
		return new ResponseEntity<>(studentId, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')") // Only Admin can access this method
	public ResponseEntity<StudentDto> updateStudent(@RequestBody @Valid StudentUpdateRequest studentUpdateRequest) {
		StudentDto updatedStudent = studentService.updateStudentData(studentUpdateRequest);
		return ResponseEntity.ok(updatedStudent);
	}

	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Only Admin can access this method
	public ResponseEntity<Void> updateStudentAddress(@PathVariable Long id, @RequestParam String address) {
		studentService.updateStudentAddress(id, address);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Only Admin can access this method
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}
}

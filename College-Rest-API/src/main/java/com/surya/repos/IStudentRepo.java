package com.surya.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surya.entitys.StudentEntity;

public interface IStudentRepo extends JpaRepository<StudentEntity, Long> {

}

package com.surya.entitys;

import java.time.LocalDateTime;
import java.util.Set;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.UpdateTimestamp;

import com.surya.enums.StudentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "students")
@Entity
@Setter
@Getter
@Filter(name = "activeFilter", condition = "status <> 'INACTIVE'")
@NoArgsConstructor
public class StudentEntity {

	@Id
	@SequenceGenerator(name = "student_id_generator", sequenceName = "student_id_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "student_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)
	private StudentStatus status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	private Set<SubjectEntity> subjects;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedAt;

}

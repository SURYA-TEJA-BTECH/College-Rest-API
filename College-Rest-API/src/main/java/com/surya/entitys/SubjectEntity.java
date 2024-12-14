package com.surya.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Table(name = "subjects")
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class SubjectEntity {

	@Id
	@SequenceGenerator(name = "subject_id_generator", sequenceName = "subject_id_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "subject_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NonNull
	@Column(nullable = false)
	private String name;

}

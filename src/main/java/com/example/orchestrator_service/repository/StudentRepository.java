package com.example.orchestrator_service.repository;

import com.example.orchestrator_service.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {}

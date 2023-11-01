package com.example.orchestrator_service.repository;

import com.example.orchestrator_service.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, String> {}

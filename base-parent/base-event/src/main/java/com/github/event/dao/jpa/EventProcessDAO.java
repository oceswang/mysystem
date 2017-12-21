package com.github.event.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.event.constants.EventStatus;
import com.github.event.entity.EventProcess;

public interface EventProcessDAO extends JpaRepository<EventProcess, Long>
{
	public List<EventProcess> getByStatus(EventStatus status);
}

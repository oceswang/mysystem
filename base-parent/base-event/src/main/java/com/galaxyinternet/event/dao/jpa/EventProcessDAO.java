package com.galaxyinternet.event.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galaxyinternet.event.constants.EventStatus;
import com.galaxyinternet.event.entity.EventProcess;

public interface EventProcessDAO extends JpaRepository<EventProcess, Long>
{
	public List<EventProcess> getByStatus(EventStatus status);
}

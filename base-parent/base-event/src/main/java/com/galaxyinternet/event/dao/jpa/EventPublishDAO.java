package com.galaxyinternet.event.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galaxyinternet.event.constants.EventStatus;
import com.galaxyinternet.event.entity.EventPublish;

public interface EventPublishDAO extends JpaRepository<EventPublish, Long>
{

	public List<EventPublish> getByStatus(EventStatus status);
}

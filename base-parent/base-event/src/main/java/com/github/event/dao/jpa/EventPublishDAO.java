package com.github.event.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.event.constants.EventStatus;
import com.github.event.entity.EventPublish;

public interface EventPublishDAO extends JpaRepository<EventPublish, Long>
{

	public List<EventPublish> getByStatus(EventStatus status);
}

package com.github.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.event.dao.jpa.EventPublishDAO;
import com.github.event.entity.EventPublish;

@Service
public class EventPublishService
{
	@Autowired
	private EventPublishDAO dao;
	
	@Transactional
	public EventPublish save(EventPublish entity)
	{
		return dao.save(entity);
	}
	

}

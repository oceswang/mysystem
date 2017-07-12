package com.galaxyinternet.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.event.dao.jpa.EventPublishDAO;
import com.galaxyinternet.event.entity.EventPublish;

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

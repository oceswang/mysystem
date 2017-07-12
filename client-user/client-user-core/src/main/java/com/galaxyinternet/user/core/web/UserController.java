package com.galaxyinternet.user.core.web;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.event.service.EventService;
import com.galaxyinternet.user.api.event.UserCreatedEvent;

@Controller
public class UserController
{
	@Autowired
	private EventService eventService;
	private AtomicLong id = new AtomicLong();
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add()
	{
		UserCreatedEvent event = new UserCreatedEvent();
		event.setUserId(id.incrementAndGet());
		event.setEventTime(LocalDateTime.now());
		eventService.publish(event);
		return "success";
	}
}

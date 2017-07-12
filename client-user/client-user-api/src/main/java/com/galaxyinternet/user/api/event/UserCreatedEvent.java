package com.galaxyinternet.user.api.event;

import java.time.LocalDateTime;

import com.galaxyinternet.event.constants.EventType;
import com.galaxyinternet.event.entity.BaseEvent;

public class UserCreatedEvent extends BaseEvent
{
	public static final EventType EVENT_TYPE = EventType.USER_CREATED;
	private Long userId;
	private LocalDateTime eventTime;
	
	
	public Long getUserId()
	{
		return userId;
	}


	public void setUserId(Long userId)
	{
		this.userId = userId;
	}


	public LocalDateTime getEventTime()
	{
		return eventTime;
	}


	public void setEventTime(LocalDateTime eventTime)
	{
		this.eventTime = eventTime;
	}


	@Override
	public EventType getEventType()
	{
		return EVENT_TYPE;
	}

}

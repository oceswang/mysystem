package com.galaxyinternet.event.handler;

import com.galaxyinternet.event.entity.BaseEvent;

public interface EventHandler<T extends BaseEvent>
{
	public void handle(T event);
}

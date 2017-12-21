package com.github.event.handler;

import com.github.event.entity.BaseEvent;

public interface EventHandler<T extends BaseEvent>
{
	public void handle(T event);
}

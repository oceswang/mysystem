package com.github.account.api.event;

import com.github.event.constants.EventType;
import com.github.event.entity.BaseEvent;

public class AccountCreatedEvent extends BaseEvent
{
	public static final EventType EVENT_TYPE = EventType.ACCOUNT_CREATED;
	
	private Long accountId;
	
	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}

	@Override
	public EventType getEventType()
	{
		return EVENT_TYPE;
	}

}

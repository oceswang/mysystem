package com.galaxyinternet.account.api.event;

import com.galaxyinternet.event.constants.EventType;
import com.galaxyinternet.event.entity.BaseEvent;

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
		// TODO Auto-generated method stub
		return null;
	}

}

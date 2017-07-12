package com.galaxyinternet.account.core.handler;


import com.galaxyinternet.account.core.service.AccountService;
import com.galaxyinternet.common.spring.ApplicationContextHolder;
import com.galaxyinternet.event.handler.EventHandler;
import com.galaxyinternet.user.api.event.UserCreatedEvent;

public class UserCreatedHandler implements EventHandler<UserCreatedEvent>
{

	@Override
	public void handle(UserCreatedEvent event)
	{
		AccountService service = ApplicationContextHolder.getInstance().getBean(AccountService.class);
		service.init(event.getUserId());
	}


}

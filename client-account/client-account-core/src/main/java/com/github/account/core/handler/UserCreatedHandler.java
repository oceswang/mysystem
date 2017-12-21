package com.github.account.core.handler;


import com.github.account.core.service.AccountService;
import com.github.common.spring.ApplicationContextHolder;
import com.github.event.handler.EventHandler;
import com.github.user.api.event.UserCreatedEvent;

public class UserCreatedHandler implements EventHandler<UserCreatedEvent>
{

	@Override
	public void handle(UserCreatedEvent event)
	{
		AccountService service = ApplicationContextHolder.getInstance().getBean(AccountService.class);
		service.init(event.getUserId());
	}


}

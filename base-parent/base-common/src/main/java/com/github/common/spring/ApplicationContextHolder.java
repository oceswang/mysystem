package com.github.common.spring;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware
{
	private static final Map<String,ApplicationContext> ctxMap = new ConcurrentHashMap<>();
	public static ApplicationContextHolder instance = new ApplicationContextHolder();

	private ApplicationContextHolder(){};

	public static ApplicationContextHolder getInstance()
	{
		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException
	{
		if(ctx != null)
		{
			ctxMap.putIfAbsent(ctx.getApplicationName(), ctx);
		}
		
	}
	
	public <T> T getBean(Class<T> clazz)
	{
		Collection<ApplicationContext> ctxs = ctxMap.values(); 
		for(ApplicationContext ctx : ctxs)
		{
			if(ctx.getBean(clazz) != null)
			{
				return ctx.getBean(clazz);
			}
		}
		return null;
	}
}

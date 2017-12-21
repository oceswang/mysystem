package com.github.event.config;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.github.common.utils.JsonUtils;
import com.github.event.constants.EventType;
import com.github.event.entity.BaseEvent;
import com.github.event.handler.EventHandler;

public class EventTypeRegistry implements InitializingBean
{

	private Set<EventType> eventTypes = new HashSet<>();
	private Map<EventType,Class<? extends BaseEvent>> eventTypeMap = new HashMap<>();
	private Map<EventType,EventHandler<BaseEvent>> handlerMap = new HashMap<>();


	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception
	{
		//扫描Eevent
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(BaseEvent.class));
		Set<BeanDefinition> beanDefs = provider.findCandidateComponents("com/github");
		for(BeanDefinition def : beanDefs)
		{
			String className = def.getBeanClassName();
			Class<? extends BaseEvent> eventClass = (Class<? extends BaseEvent>)Class.forName(className);
			Field field = FieldUtils.getField(eventClass, "EVENT_TYPE");
			if(field == null)
			{
				throw new RuntimeException("EVENT_TYPE is required for class "+className);
			}
			EventType eventType = (EventType)field.get(null);
			eventTypeMap.put(eventType, eventClass);
		}
		eventTypes.addAll(eventTypeMap.keySet());
		//扫描EeventHandler
		provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(EventHandler.class));
		beanDefs = provider.findCandidateComponents("com/github");
		for(BeanDefinition def : beanDefs)
		{
			String className = def.getBeanClassName();
			Class<EventHandler<BaseEvent>> handlerClass = (Class<EventHandler<BaseEvent>>)Class.forName(className);
			Type type = handlerClass.getGenericInterfaces()[0];
			if(!(type instanceof ParameterizedType))
			{
				throw new Exception(String.format("Class %s is not instance of ParameterizedType"));
			}
			Type actualType = ((ParameterizedType)type).getActualTypeArguments()[0];
			String eventClassName = actualType.getTypeName();
			Class<? extends BaseEvent> eventClass = (Class<? extends BaseEvent>)Class.forName(eventClassName);
			Field field = FieldUtils.getField(eventClass, "EVENT_TYPE");
			if(field == null)
			{
				throw new RuntimeException("EVENT_TYPE is required for class "+className);
			}
			EventType eventType = (EventType)field.get(null);
			if(!handlerMap.containsKey(eventClassName))
			{
				handlerMap.put(eventType, handlerClass.newInstance());
			}
		}
	}

	public Set<EventType> getEventTypes()
	{
		return eventTypes;
	}

	public BaseEvent getEvent(EventType eventType, String payload)
	{
		Class<? extends BaseEvent> clazz = eventTypeMap.get(eventType);
		return JsonUtils.json2Object(payload, clazz);
	}
	
	public EventHandler<BaseEvent> getHandler(EventType eventType)
	{
		return handlerMap.get(eventType);
	}

}

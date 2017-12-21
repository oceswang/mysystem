package com.github.event.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.InitializingBean;

import com.github.common.spring.ApplicationContextHolder;
import com.github.event.constants.EventType;
import com.github.event.service.EventService;

public class ConsumerEndpoint implements InitializingBean
{
	private static final Logger logger = LoggerFactory.getLogger(ConsumerEndpoint.class);
	private EventTypeRegistry eventTypeRegistry;
	private ConnectionFactory cf;
	private String group;
	private EventService eventService = ApplicationContextHolder.getInstance().getBean(EventService.class);
	
	public ConsumerEndpoint(EventTypeRegistry eventTypeRegistry, ConnectionFactory cf, String group)
	{
		super();
		this.eventTypeRegistry = eventTypeRegistry;
		this.cf = cf;
		this.group = group;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Set<EventType> eventTypes = eventTypeRegistry.getEventTypes();
		List<Queue> queues = eventTypes.stream()
										.map(evnetType -> {
											Queue queue = new Queue(evnetType.name()+"."+group);
											logger.debug("Lisning Queue - "+queue.getName());
											return queue;
										})
										.collect(Collectors.toList());
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
		Queue[] array = new Queue[eventTypes.size()];
		container.addQueues(queues.toArray(array));
		container.setExposeListenerChannel(true);  
        container.setMaxConcurrentConsumers(1);  
        container.setConcurrentConsumers(1);  
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); 
        container.setMessageListener(eventService);
        container.start();
		
	}

}

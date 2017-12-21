package com.github.event.config;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.InitializingBean;

import com.github.event.constants.EventType;

public class ProducerEndpoint implements InitializingBean
{
	private static final Logger logger = LoggerFactory.getLogger(ProducerEndpoint.class);
	private EventTypeRegistry eventTypeRegistry;
	private ConnectionFactory cf;
	private String group;
	
	public ProducerEndpoint(EventTypeRegistry eventTypeRegistry, ConnectionFactory cf, String group)
	{
		super();
		this.eventTypeRegistry = eventTypeRegistry;
		this.cf = cf;
		this.group = group;
	}


	@Override
	public void afterPropertiesSet() throws Exception
	{
		RabbitAdmin admin = new RabbitAdmin(cf);
		FanoutExchange exchange = null;
		Queue queue = null;
		Set<EventType> eventTypes = eventTypeRegistry.getEventTypes();
		if(eventTypes != null)
		{
			for(EventType eventType : eventTypes)
			{
				exchange = new FanoutExchange(eventType.name());
				admin.declareExchange(exchange);
				queue = new Queue(eventType.name()+"."+group);
				admin.declareQueue(queue);
				admin.declareBinding(BindingBuilder.bind(queue).to(exchange));
				logger.debug(String.format("Init Queue - %s", queue.getName()));
			}
		}
		
	}
}

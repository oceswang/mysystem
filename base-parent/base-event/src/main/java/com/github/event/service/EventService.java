package com.github.event.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.common.utils.JsonUtils;
import com.github.event.config.EventTypeRegistry;
import com.github.event.constants.EventStatus;
import com.github.event.constants.EventType;
import com.github.event.dao.jpa.EventProcessDAO;
import com.github.event.dao.jpa.EventPublishDAO;
import com.github.event.entity.BaseEvent;
import com.github.event.entity.EventProcess;
import com.github.event.entity.EventPublish;
import com.github.event.handler.EventHandler;
import com.rabbitmq.client.Channel;

@Service
public class EventService implements ChannelAwareMessageListener
{
	private static final Logger logger = LoggerFactory.getLogger(EventService.class);
	@Autowired
	private EventPublishDAO publishDAO;
	@Autowired
	private EventProcessDAO processDAO;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private EventTypeRegistry eventTypeRegistry;

	@Transactional
	public EventPublish publish(BaseEvent event)
	{
		event.setEventId(UUID.randomUUID().toString());
		EventPublish eventPublish = new EventPublish();
		eventPublish.setCreatedTime(LocalDateTime.now());
		eventPublish.setStatus(EventStatus.NEW);
		eventPublish.setPayload(JsonUtils.object2Json(event));
		eventPublish.setEventId(event.getEventId());
		eventPublish.setType(event.getEventType());
		return publishDAO.save(eventPublish);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void onMessage(Message message, Channel channel) throws Exception
	{
		byte[] bytes = message.getBody();
		String msg = new String(bytes);
		logger.debug(String.format("Message: %s, Channel: %s", msg, channel));
		Map<String, Object> map = JsonUtils.json2Object(msg, Map.class);
		if (map != null && map.containsKey("eventType"))
		{
			EventType eventType = EventType.valueOf((String) map.get("eventType"));
			EventProcess process = new EventProcess();
			process.setEventId((String) map.get("eventId"));
			process.setType(eventType);
			process.setStatus(EventStatus.RECEIVED);
			process.setPayload(msg);
			processDAO.save(process);
		}
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}

	@Transactional
	public void publishEvent()
	{
		List<EventPublish> list = publishDAO.getByStatus(EventStatus.NEW);
		list.stream().forEach(item -> {
			executeEventHandler(() -> {
				item.setStatus(EventStatus.PUBLISHED);
				Message message = new Message(item.getPayload().getBytes(), new MessageProperties());
				publishDAO.save(item);
				rabbitTemplate.send(item.getType().name(), "", message);
				return item;
			});
		});
	}

	@Scheduled(fixedRate = 500L)
	public void publisTask()
	{
		publishEvent();
	}
	
	@Scheduled(fixedRate = 500L)
	public void processTask()
	{
		processEvent();
	}

	@Transactional
	public void processEvent()
	{
		List<EventProcess> list = processDAO.getByStatus(EventStatus.RECEIVED);
		list.stream().forEach(item -> {
			EventType type = item.getType();
			EventHandler<BaseEvent> handler = eventTypeRegistry.getHandler(type);
			if (handler == null)
			{
				item.setStatus(EventStatus.IGNORED);
				processDAO.saveAndFlush(item);
				return;
			}
			executeEventHandler(() -> {
				BaseEvent event = eventTypeRegistry.getEvent(type, item.getPayload());
				handler.handle(event);
				return null;
			});
			item.setStatus(EventStatus.PROCESSED);
			processDAO.saveAndFlush(item);
		});
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T executeEventHandler(Supplier<T> supplier)
	{
		return supplier.get();
	}

}

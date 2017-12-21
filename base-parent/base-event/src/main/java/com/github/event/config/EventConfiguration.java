package com.github.event.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.github.common.spring.ApplicationContextHolder;


@Configuration
@EnableScheduling
@PropertySource("rmq.properties")
public class EventConfiguration
{
	@Value("${rmq.addresses}")
	private String addresses;
	@Value("${rmq.virtualHost}")
	private String virtualHost;
	@Value("${rmq.username}")
	private String userName;
	@Value("${rmq.password}")
	private String password;
	@Value("${rmq.group}")
	private String group;
	@Bean
	public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }
	
	@Bean
	public EventTypeRegistry eventRegistry()
	{
		return new EventTypeRegistry();
	}
	@Bean
	public ConnectionFactory connectionFactory()
	{
		CachingConnectionFactory cf = new CachingConnectionFactory();
		cf.setAddresses(addresses);
		cf.setUsername(userName);
		cf.setPassword(password);
		cf.setVirtualHost(virtualHost);
		cf.setPublisherConfirms(true);
		cf.setChannelCacheSize(20);
		return cf;
	}

	@Bean
	public ProducerEndpoint producerEndpoint()
	{
		return new ProducerEndpoint(eventRegistry(), connectionFactory(), group);
	}
	
	@Bean
	public ConsumerEndpoint cunsumerEndpoint()
	{
		return new ConsumerEndpoint(eventRegistry(), connectionFactory(), group); 
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate()
	{
		return new RabbitTemplate(connectionFactory());
	}
	
	@Bean
	public TaskScheduler taskScheduler()
	{
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.initialize();
		taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors()*2);
		taskScheduler.setThreadNamePrefix("EventExecutor-");
		return taskScheduler;
	}
	
	
}

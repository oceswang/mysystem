package com.galaxyinternet.account.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.galaxyinternet.common.config.JPAConfigutation;
import com.galaxyinternet.common.spring.ApplicationContextHolder;
import com.galaxyinternet.event.config.EventConfiguration;
@SpringBootApplication
@Import({EventConfiguration.class, JPAConfigutation.class })
@ComponentScan({"com.galaxyinternet.**.service", "com.galaxyinternet.**.web"})
public class AccountApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(AccountApplication.class, args);
	}
	
	@Bean
	public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }
}

package com.github.user.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.github.common.config.JPAConfigutation;
import com.github.common.spring.ApplicationContextHolder;
import com.github.event.config.EventConfiguration;

@SpringBootApplication
@Import({EventConfiguration.class, JPAConfigutation.class })
@ComponentScan({"com.github.**.service", "com.github.**.web"})
public class UserApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(UserApplication.class, args);
	}
	
	@Bean
	public ApplicationContextHolder applicationContextHolder() {
        return ApplicationContextHolder.getInstance();
    }

}

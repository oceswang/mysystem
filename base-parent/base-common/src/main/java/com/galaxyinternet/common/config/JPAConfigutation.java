package com.galaxyinternet.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "com.galaxyinternet.**.entity",
        "org.springframework.data.jpa.convert.threeten"
})
@EnableJpaRepositories("com.galaxyinternet.**.dao.jpa")
public class JPAConfigutation
{

}

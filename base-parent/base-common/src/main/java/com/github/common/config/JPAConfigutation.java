package com.github.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "com.github.**.entity",
        "org.springframework.data.jpa.convert.threeten"
})
@EnableJpaRepositories("com.github.**.dao.jpa")
public class JPAConfigutation
{

}

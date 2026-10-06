package com.furkandogan.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
@EnableCaching
@ComponentScan (basePackages = {"com.furkandogan"})
@EntityScan (basePackages = {"com.furkandogan"})
@EnableJpaRepositories (basePackages = {"com.furkandogan"})
@SpringBootApplication
public class GalleristprojectApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(GalleristprojectApplicationStarter.class, args);
	}

}

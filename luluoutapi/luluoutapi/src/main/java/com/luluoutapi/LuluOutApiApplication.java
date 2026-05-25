package com.luluoutapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class LuluOutApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuluOutApiApplication.class, args);
		log.info("::::::::::::::::::::::LuluOutApi Application Started::::::::::::::::::::::");

	}

}

package com.FireFacilAuto;


import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@EnableCaching
@EnableAsync
public class FireFacilAutoApplication {

	public static void main(String[] args) {
		System.getProperties().forEach((key, value) -> {
			log.info(key + ": " + value);
		});
		SpringApplication.run(FireFacilAutoApplication.class, args);
	}

}

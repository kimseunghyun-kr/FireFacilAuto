package com.FireFacilAuto;

import com.FireFacilAuto.domain.BuildingClassificationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class FireFacilAutoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(FireFacilAutoApplication.class, args);

		ConfigurableApplicationContext applicationContext = SpringApplication.run(FireFacilAutoApplication.class, args);

		// Retrieve the ClassificationConfiguration bean
		BuildingClassificationConfig classificationConfiguration = applicationContext.getBean(BuildingClassificationConfig.class);

		log.info("loaded classifications: {}", classificationConfiguration.getClassifications());
	}

}

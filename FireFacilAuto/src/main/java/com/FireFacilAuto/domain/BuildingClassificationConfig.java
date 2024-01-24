package com.FireFacilAuto.domain;


import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "classifications")
@Slf4j
public class BuildingClassificationConfig {

    public BuildingClassificationConfig() {    }

    public List<Classification> classifications;

    @PostConstruct
    public void postConstruct() {
        log.info("Loaded classifications 22222: {}", classifications);
    }

}

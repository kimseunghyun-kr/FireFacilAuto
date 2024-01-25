package com.FireFacilAuto.domain.configImport;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "building")
@Data
public class ClassificationList {
    private List<Classification> classifications;
}

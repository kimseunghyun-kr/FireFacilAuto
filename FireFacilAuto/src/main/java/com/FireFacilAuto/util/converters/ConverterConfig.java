package com.FireFacilAuto.util.converters;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BuildingLawFormsToBuildingLawFieldsConverter());
        registry.addConverter(new FloorLawFormsToFloorLawFieldsConverter());
        registry.addConverter(new ApiResponseItemToBuildingConverter());
        registry.addConverter(new FormBuildingDTOToBuildingConverter());
        registry.addConverter(new FormFloorDTOToFloorConverter());
    }
}

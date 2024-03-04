package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LawFieldConfig {

    @Bean
    public PossibleLawFieldRegistry possibleLawFieldRegistry() {
        return new PossibleLawFieldRegistry();
    }

    // Register your enums with the registry
    @Bean
    public PossibleLawFieldRegistrar possibleLawFieldRegistrar(PossibleLawFieldRegistry registry) {
        return new PossibleLawFieldRegistrar(registry);
    }

    @Bean
    public ClauseFactory clauseFactory(PossibleLawFieldRegistry registry) {
        return new ClauseFactory(registry);
    }
}


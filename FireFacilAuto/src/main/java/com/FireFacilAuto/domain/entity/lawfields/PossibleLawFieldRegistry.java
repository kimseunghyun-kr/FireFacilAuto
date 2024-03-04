package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleLawField;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PossibleLawFieldRegistry {
    private final Map<String, PossibleLawField> registry = new ConcurrentHashMap<>();

    public <T extends Enum<T> & PossibleLawField> void register(Class<T> enumType) {
        for (T enumValue : enumType.getEnumConstants()) {
            registry.put(enumValue.getLawFieldName(), enumValue);
        }
    }

    public <T extends Enum<T> & PossibleLawField> T get(Class<T> enumType, String fieldName) {
        return enumType.cast(registry.get(fieldName));
    }
}


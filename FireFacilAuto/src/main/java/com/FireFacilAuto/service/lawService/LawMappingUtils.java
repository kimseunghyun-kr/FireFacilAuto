package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LawMappingUtils {

    static void floorResultListMajorCodeMapper(List<FloorResults> floorResultsList, Integer[] target) {
        log.info("class : {}, spec : {}", target[0], target[1]);
        for (FloorResults survivingResults : floorResultsList) {
            survivingResults.numericSetter(target[0], target[1]);
            log.info("surviving at FloorResulTMajorCodeMapper, {}", survivingResults);
        }
    }

    static Conditions getCondition(List<Conditions> conditions, String fieldName) {
        return conditions.stream()
                .filter(c -> c.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow();
    }

}

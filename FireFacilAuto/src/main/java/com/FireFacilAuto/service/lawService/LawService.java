package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.repository.BuildingLawFieldsRepository;
import com.FireFacilAuto.repository.FloorLawFieldsRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Service
@Slf4j
public  class LawService {

    private final ConversionService conversionService;
    private final BuildingLawFieldsRepository blawFieldRepository;
    private final FloorLawFieldsRepository flawFieldRepository;


    @Autowired
    public LawService(ConversionService conversionService, BuildingLawFieldsRepository blawFieldRepository, FloorLawFieldsRepository flawFieldRepository) {
        this.conversionService = conversionService;
        this.blawFieldRepository = blawFieldRepository;
        this.flawFieldRepository = flawFieldRepository;
    }

    public <T> Page<T> getPaginatedLaws(int page, int size, Class<T> entityType) {
        Pageable pageable = PageRequest.of(page, size);

        if (BuildingLawFields.class.equals(entityType)) {
            return (Page<T>) blawFieldRepository.findAll(pageable);
        } else if (FloorLawFields.class.equals(entityType)) {
            return (Page<T>) flawFieldRepository.findAll(pageable);
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }
    }


    public List<BuildingLawFields> makeBuildingLaw(BuildingLawForms form, List<String> purposeClass, List<String> purposeSpec) {
        String stringInput = form.buildingPurpose;
        List<Integer[]> purposeList = purposeMapper(stringInput, purposeClass, purposeSpec);
        return lawFactory(form, purposeList, BuildingLawFields.class , blawFieldRepository);
    }

    public List<FloorLawFields> makeFloorLaw(FloorLawForms form , List<String> purposeClass, List<String> purposeSpec) {
        String stringInput = form.floorPurpose;
        List<Integer[]> purposeList = purposeMapper(stringInput, purposeClass, purposeSpec);
        return lawFactory(form, purposeList, FloorLawFields.class , flawFieldRepository);
    }


    private <T> List<T> lawFactory(Object form, List<Integer[]> purposeList, Class<T> entityType, JpaRepository<T, Long> repository) {
        List<T> result = new LinkedList<>();
        Map<String, ComparisonOperator> conditionMap = getConditions(form);
        for (Integer[] purposePair : purposeList) {
            T template = conversionService.convert(form, entityType);
            assert template != null;
            setPurpose(template, purposePair);
            List<Conditions> conditionsList = conditionMap.entrySet().stream()
                    .map(entry -> createCondition(entry.getKey(), entry.getValue(), template))
                    .toList();
            setConditions(template, conditionsList);
            result.add(template);
            saveLaw(template, repository);
        }
        return result;
    }

    private Map<String, ComparisonOperator> getConditions(Object form) {
        if (form instanceof BuildingLawForms) {
            return ((BuildingLawForms) form).getConditions();
        } else if (form instanceof FloorLawForms) {
            return ((FloorLawForms) form).getConditions();
        }
        return Map.of();
    }

    private <T> void setPurpose(T template, Integer[] purposePair) {
        if (template instanceof BuildingLawFields) {
            ((BuildingLawFields) template).setBuildingClassification(purposePair[0]);
            ((BuildingLawFields) template).setBuildingSpecification(purposePair[1]);
        } else if (template instanceof FloorLawFields) {
            ((FloorLawFields) template).setFloorClassification(purposePair[0]);
            ((FloorLawFields) template).setFloorSpecification(purposePair[1]);
        }
    }

    private <T> Conditions createCondition(String fieldName, ComparisonOperator operator, T template) {
        Conditions condition = new Conditions();
        condition.setFieldName(fieldName);
        condition.setOperator(operator);
        if (template instanceof BuildingLawFields) {
            condition.setBuildingLawFields((BuildingLawFields) template);
        } else if (template instanceof FloorLawFields) {
            condition.setFloorLawFields((FloorLawFields) template);
        }
        return condition;
    }

    private <T> void setConditions(T template, List<Conditions> conditionsList) {
        if (template instanceof BuildingLawFields) {
            ((BuildingLawFields) template).setConditionsList(conditionsList);
        } else if (template instanceof FloorLawFields) {
            ((FloorLawFields) template).setConditionsList(conditionsList);
        }
    }

    private <T> void saveLaw(T template, JpaRepository<T, Long> repository) {
        repository.save(template);
    }

    private List<Integer[]> purposeMapper(String parsable, List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> resultList = new LinkedList<>();
        if (parsable != null && !parsable.replace(" ", "").isEmpty()) {
            resultList.addAll(parser(parsable));
        }
        resultList.addAll(parser(purposeClass, purposeSpec));
        return resultList;
    }


//    remember to change the Integer.parseInt into mapper class for purposeStringName -> IntegerCode;
    private List<Integer[]> parser(List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> resultList = new LinkedList<>();
        if(purposeClass.isEmpty() || purposeSpec.isEmpty() || purposeClass.size() != purposeSpec.size()) {
            return resultList;
        }
        resultList.addAll(IntStream.range(0, purposeClass.size()).mapToObj(i -> new Integer[] {Integer.parseInt(purposeClass.get(i),
                Integer.parseInt(purposeSpec.get(i)))}).toList());

        return resultList;
    }

    private List<Integer[]> parser(String parsable) {
        List<Integer[]> result = new LinkedList<>();
        String[] conditions = parsable.split(",");
        for (String condition : conditions) {
            condition = condition.replace(" ", "");
            Matcher defaultRegex = Pattern.compile("\\((-?\\d+) ,-?\\d+\\)").matcher(condition);
            if(defaultRegex.matches()) {
                Integer[] added = {Integer.parseInt(defaultRegex.group(1)),
                        Integer.parseInt(defaultRegex.group(2))};
                result.add(added);
                continue;
            }
            Matcher classRegex = Pattern.compile("\\(\\[(-?\\d+-\\d+)],-1\\)").matcher(condition);
            if(classRegex.matches()){
                String[] digitwithinBrackets = classRegex.group(1).split("-");
                int low = Integer.parseInt(digitwithinBrackets[0]);
                int high = Integer.parseInt(digitwithinBrackets[1]);
                for(int i = low; i <= high; i++) {
                    Integer[] added = {i, -1};
                    result.add(added);
                }
                continue;
            }
            Matcher specRegex = Pattern.compile("\\((\\d+),\\[(\\d+)-(\\d+)]\\)").matcher(condition);
            if(specRegex.matches()) {
                int number1 = Integer.parseInt(specRegex.group(1)); // Extracting number1
                int number2 = Integer.parseInt(specRegex.group(2)); // Extracting number2
                int number3 = Integer.parseInt(specRegex.group(3)); // Extracting number3
                for(int i = number2 ; i <= number3 ; i++) {
                    Integer[] added = {number1 , i};
                    result.add(added);
                }
            }
        }
        return result;
    }


    public List<BuildingLawFields> getLawsWithApplicablePurpose(Building building) {
        return blawFieldRepository.findMatchingPurpose(building.getBuildingClassification(),
                building.getBuildingSpecification());
    }

    public List<FloorLawFields> getLawsWithApplicablePurpose(Floor floor) {
        return flawFieldRepository.findMatchingPurpose(floor.getFloorClassification(),
                floor.getFloorSpecification());
    }
}

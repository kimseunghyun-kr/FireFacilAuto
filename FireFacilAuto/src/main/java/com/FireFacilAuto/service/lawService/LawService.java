package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Service
@Slf4j
public  class LawService {

    private final ConversionService conversionService;


    @Autowired
    public LawService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public List<BuildingLawFields> makeBuildingLaw(BuildingLawForms form, List<String> purposeClass, List<String> purposeSpec) {
        String stringInput = form.buildingPurpose;
        List<Integer[]> purposeList = purposeMapper(stringInput, purposeClass, purposeSpec);
        return buildingLawFactory(form, purposeList);
    }

    private List<BuildingLawFields> buildingLawFactory(BuildingLawForms form, List<Integer[]> purposeList) {
        List<BuildingLawFields> result = new LinkedList<>();
        for(Integer[] purposePair : purposeList) {
            BuildingLawFields template = conversionService.convert(form, BuildingLawFields.class);
            assert template != null;
            template.setBuildingClassification(purposePair[0]);
            template.setBuildingSpecification(purposePair[1]);
            result.add(template);
        }
        return result;
    }

    public List<FloorLawFields> makeFloorLaw(FloorLawForms form , List<String> purposeClass, List<String> purposeSpec) {
        String stringInput = form.floorPurpose;
        List<Integer[]> purposeList = purposeMapper(stringInput, purposeClass, purposeSpec);
        return floorLawFactory(form, purposeList);
    }

    private List<FloorLawFields> floorLawFactory(FloorLawForms form, List<Integer[]> purposeList) {
        List<FloorLawFields> result = new LinkedList<>();
        for(Integer[] purposePair : purposeList) {
            FloorLawFields template = conversionService.convert(form, FloorLawFields.class);
            assert template != null;
            template.setFloorClassification(purposePair[0]);
            template.setFloorSpecification(purposePair[1]);
            result.add(template);
        }
        return result;
    }

    private List<Integer[]> purposeMapper (String parsable , List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> resultList = new LinkedList<>();
        if (parsable != null && !parsable.replace(" ", "").isEmpty()) {
            List<Integer[]> parsedResult = parser(parsable);
            resultList.addAll(parsedResult);
        }
        List<Integer[]> parsedPurpose = parser(purposeClass, purposeSpec);
        resultList.addAll(parsedPurpose);

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
}

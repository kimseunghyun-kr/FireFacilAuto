package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import com.FireFacilAuto.repository.BuildingLawFieldsRepository;
import com.FireFacilAuto.repository.FloorLawFieldsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
    private final BuildingLawFieldsRepository blawFieldRepository;
    private final FloorLawFieldsRepository flawFieldRepository;



    @Autowired
    public LawService(ConversionService conversionService, BuildingLawFieldsRepository blawFieldRepository, FloorLawFieldsRepository flawFieldRepository) {
        this.conversionService = conversionService;
        this.blawFieldRepository = blawFieldRepository;
        this.flawFieldRepository = flawFieldRepository;
    }



    @SuppressWarnings("unchecked")
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


    public List<BuildingLawFields> makeBuildingLaw(BuildingLawForms form, String purposeString, List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> purposeList = purposeMapper(purposeString, purposeClass, purposeSpec);
        log.info("list of purposes: {}", purposeList);
        return lawFactory(form, purposeList, BuildingLawFields.class, blawFieldRepository);
    }

    public List<FloorLawFields> makeFloorLaw(FloorLawForms form, String purposeString, List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> purposeList = purposeMapper(purposeString, purposeClass, purposeSpec);
        return lawFactory(form, purposeList, FloorLawFields.class, flawFieldRepository);
    }


    private <T> List<T> lawFactory(Object form, List<Integer[]> purposeList, Class<T> entityType, JpaRepository<T, Long> repository) {
        List<T> result = new LinkedList<>();
        for (Integer[] purposePair : purposeList) {
            T template = conversionService.convert(form, entityType);
            log.info("template converted : {}", template);
            assert template != null;
            setPurpose(template, purposePair);
            result.add(template);
            saveLaw(template, repository);
        }
        return result;
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


    public <T> void saveLaw(T template, JpaRepository<T, Long> repository) {
        repository.save(template);
        log.info("template saved : {}", repository.findAll());
    }

    private List<Integer[]> purposeMapper(String parsable, List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> resultList = new LinkedList<>();
        if (parsable != null && !parsable.replace(" ", "").isEmpty()) {
            log.info("stringparser activated, string : {}", parsable);
            resultList.addAll(parsePurposeStringToInteger(parsable));
        }
        resultList.addAll(parseListPurposeInputToPurpose(purposeClass, purposeSpec));
        return resultList;
    }


    //    remember to change the Integer.parseInt into mapper class for purposeStringName -> IntegerCode;
    private List<Integer[]> parseListPurposeInputToPurpose (List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> resultList = new LinkedList<>();
        if (purposeClass.isEmpty() || purposeSpec.isEmpty() || purposeClass.size() != purposeSpec.size()) {
            return resultList;
        }
        resultList.addAll(IntStream.range(0, purposeClass.size()).mapToObj(i -> new Integer[]{Integer.parseInt(purposeClass.get(i)),
                Integer.parseInt(purposeSpec.get(i))}).toList());

        return resultList;
    }

    private List<Integer[]> parsePurposeStringToInteger(String parsable) {
        List<Integer[]> result = new LinkedList<>();
        String[] conditions = parsable.split("&");
        for (String condition : conditions) {
            condition = condition.replace(" ", "");
            log.info("condition being matched : {}", condition);
            Matcher defaultRegex = Pattern.compile("\\((-?\\d+),(-?\\d)+\\)").matcher(condition);
            if (defaultRegex.matches()) {
                Integer[] added = {Integer.parseInt(defaultRegex.group(1)),
                        Integer.parseInt(defaultRegex.group(2))};

                log.info("regex matched, adding : {},  {}", added[0], added[1]);
                result.add(added);
                continue;
            }
            Matcher classRegex = Pattern.compile("\\(\\[(-?\\d+-\\d+)],-1\\)").matcher(condition);
            if (classRegex.matches()) {
                String[] digitwithinBrackets = classRegex.group(1).split("-");
                int low = Integer.parseInt(digitwithinBrackets[0]);
                int high = Integer.parseInt(digitwithinBrackets[1]);
                for (int i = low; i <= high; i++) {
                    Integer[] added = {i, -1};
                    log.info("regex matched, adding : {},  {}", added[0], added[1]);
                    result.add(added);
                }
                continue;
            }
            Matcher specRegex = Pattern.compile("\\((\\d+),\\[(\\d+)-(\\d+)]\\)").matcher(condition);
            if (specRegex.matches()) {
                int number1 = Integer.parseInt(specRegex.group(1)); // Extracting number1
                int number2 = Integer.parseInt(specRegex.group(2)); // Extracting number2
                int number3 = Integer.parseInt(specRegex.group(3)); // Extracting number3
                for (int i = number2; i <= number3; i++) {
                    Integer[] added = {number1, i};
                    log.info("regex matched, adding : {},  {}", added[0], added[1]);
                    result.add(added);
                }
            }
        }
        return result;
    }
}
package com.FireFacilAuto.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Service
@Slf4j
public  class ControllerPurposeInputSpringParser {


    private List<Integer[]> purposeMapper(String parsable, List<String> purposeClass, List<String> purposeSpec) {
        List<Integer[]> resultList = new LinkedList<>();
        if (parsable != null && !parsable.replace(" ", "").isEmpty()) {
            log.info("stringparser activated, string : {}", parsable);
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
        resultList.addAll(IntStream.range(0, purposeClass.size()).mapToObj(i -> new Integer[] {Integer.parseInt(purposeClass.get(i)),
                Integer.parseInt(purposeSpec.get(i))}).toList());

        return resultList;
    }

    private List<Integer[]> parser(String parsable) {
        List<Integer[]> result = new LinkedList<>();
        String[] conditions = parsable.split("&");
        for (String condition : conditions) {
            condition = condition.replace(" ", "");
            log.info("condition being matched : {}", condition);
            Matcher defaultRegex = Pattern.compile("\\((-?\\d+),(-?\\d)+\\)").matcher(condition);
            if(defaultRegex.matches()) {
                Integer[] added = {Integer.parseInt(defaultRegex.group(1)),
                        Integer.parseInt(defaultRegex.group(2))};

                log.info("regex matched, adding : {},  {}", added[0], added[1]);
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
                    log.info("regex matched, adding : {},  {}", added[0], added[1]);
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
                    log.info("regex matched, adding : {},  {}", added[0], added[1]);
                    result.add(added);
                }
            }
        }
        return result;
    }





}

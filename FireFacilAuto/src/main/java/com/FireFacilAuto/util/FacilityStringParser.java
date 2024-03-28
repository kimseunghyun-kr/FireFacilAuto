package com.FireFacilAuto.util;

import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.building.field.StringField;

public class FacilityStringParser {

    public static String facilityStringParser(Field stringField, String addString) {
        if(!(stringField instanceof StringField castedStringField)) {
            throw new UnsupportedOperationException("the passed Field is not a StringField");
        }
        if(!castedStringField.getFieldName().contains("extraFacility")) {
            throw new UnsupportedOperationException("the passed Field does not contain extraFacility. do not use this parser");
        }
        String originalString = castedStringField.getValue();
        String resultString;
        if (originalString.isEmpty()) {
            resultString = addString;
        } else {
            resultString = originalString + "/" + addString;
        }
        return resultString;
    }
}

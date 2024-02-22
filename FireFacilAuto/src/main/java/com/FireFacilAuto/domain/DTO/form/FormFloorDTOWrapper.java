package com.FireFacilAuto.domain.DTO.form;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class FormFloorDTOWrapper {
    public List<FormFloorDTO> listWrapper = new LinkedList<>();
}

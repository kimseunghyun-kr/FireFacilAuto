package com.FireFacilAuto.domain.DTO.form;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class FormFloorDTOWrapper implements Serializable {
    public List<FormFloorDTO> listWrapper = new LinkedList<>();
}

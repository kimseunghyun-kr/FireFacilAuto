package com.FireFacilAuto.domain.configImport;

import lombok.Data;

import java.util.List;

@Data
public class Classification {
    public String name;
    public Integer number;
    public List<Specification> specifications;

}

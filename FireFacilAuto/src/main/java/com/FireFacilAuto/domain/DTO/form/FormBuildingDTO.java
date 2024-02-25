package com.FireFacilAuto.domain.DTO.form;

import com.FireFacilAuto.domain.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class FormBuildingDTO implements Serializable {

    public Address juso;
    public Integer overgroundFloors;
    public Integer undergroundFloors;
    public Integer floor;
    public Integer buildingMaterial;
    public Double GFA;
    public Integer classification;
    public Integer specification;

    public Long length;

    public LocalDate dateOfApproval;
}


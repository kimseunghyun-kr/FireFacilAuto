package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.building.field.DoubleField;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.building.field.IntegerField;
import com.FireFacilAuto.domain.entity.building.field.LocalDateField;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BuildingAttributes {

    public boolean manualBuildFlag;
    public int buildingHumanCapacity;
    public int buildingClassification;
    public int buildingSpecification;
    public int buildingMaterial;
    public int undergroundFloors;
    public int overgroundFloors;
    public int totalFloors;
    public double GFA;
    public LocalDate approvalDate;

    public BuildingAttributes totalFloors() {
        this.totalFloors = this.undergroundFloors + this.overgroundFloors;
        return this;
    }


    public Map<String, Field> toFieldMap() {

        Map<String, Field> buildingFieldList = new ConcurrentHashMap<>();

        buildingFieldList.put("buildingHumanCapacity", new IntegerField("buildingHumanCapacity", this.buildingHumanCapacity, Integer.class));
        buildingFieldList.put("buildingClassification", new IntegerField("buildingClassification", this.buildingClassification, Integer.class));
        buildingFieldList.put("buildingSpecification", new IntegerField("buildingSpecification", this.buildingSpecification, Integer.class));
        buildingFieldList.put("buildingMaterial", new IntegerField("buildingMaterial", this.buildingMaterial, Integer.class));
        buildingFieldList.put("undergroundFloors", new IntegerField("undergroundFloors", this.undergroundFloors, Integer.class));
        buildingFieldList.put("overgroundFloors", new IntegerField("overgroundFloors", this.overgroundFloors, Integer.class));
        buildingFieldList.put("totalFloors", new IntegerField("totalFloors", this.totalFloors, Integer.class));
        buildingFieldList.put("GFA", new DoubleField("GFA", this.GFA, Double.class));
        buildingFieldList.put("dateOfApproval", new LocalDateField("dateOfApproval", this.approvalDate, LocalDate.class));

        return buildingFieldList;
    }

    public Building toBuilding() {
        Building building = new Building();
        building.setBuildingFieldMap(this.toFieldMap());
        return building;
    }

    public static BuildingAttributesBuilder builder() {
        return new BuildingAttributesBuilder();
    }
}
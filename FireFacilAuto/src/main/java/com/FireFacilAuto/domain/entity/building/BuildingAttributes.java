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

        buildingFieldList.put("buildingHumanCapacity", new IntegerField("bhc", this.buildingHumanCapacity, Integer.class));
        buildingFieldList.put("buildingClassification", new IntegerField("bc", this.buildingClassification, Integer.class));
        buildingFieldList.put("buildingSpecification", new IntegerField("bs", this.buildingSpecification, Integer.class));
        buildingFieldList.put("buildingMaterial", new IntegerField("bm", this.buildingMaterial, Integer.class));
        buildingFieldList.put("undergroundFloors", new IntegerField("ugf", this.undergroundFloors, Integer.class));
        buildingFieldList.put("overgroundFloors", new IntegerField("ogf", this.overgroundFloors, Integer.class));
        buildingFieldList.put("totalFloors", new IntegerField("tf", this.totalFloors, Integer.class));
        buildingFieldList.put("GFA", new DoubleField("gfa", this.GFA, Double.class));
        buildingFieldList.put("dateOfApproval", new LocalDateField("approvalDate", this.approvalDate, LocalDate.class));

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
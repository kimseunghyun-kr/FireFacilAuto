package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.building.field.DoubleField;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.building.field.IntegerField;
import com.FireFacilAuto.domain.entity.building.field.LocalDateField;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class BuildingAttributes {

    public boolean manualBuildFlag;
    private int buildingHumanCapacity;
    private int buildingClassification;
    private int buildingSpecification;
    private int buildingMaterial;
    private int undergroundFloors;
    private int overgroundFloors;
    private int totalFloors;
    private double gfa;
    private LocalDate approvalDate;

    @Builder
    private static BuildingAttributes of(boolean manualBuildFlag, int buildingHumanCapacity, int buildingClassification,
                                         int buildingSpecification, int buildingMaterial, int undergroundFloors,
                                         int overgroundFloors, double gfa, LocalDate approvalDate) {
        BuildingAttributes buildingAttributes = new BuildingAttributes();
        buildingAttributes.manualBuildFlag = manualBuildFlag;
        buildingAttributes.buildingHumanCapacity = buildingHumanCapacity;
        buildingAttributes.buildingClassification = buildingClassification;
        buildingAttributes.buildingSpecification = buildingSpecification;
        buildingAttributes.buildingMaterial = buildingMaterial;
        buildingAttributes.undergroundFloors = undergroundFloors;
        buildingAttributes.overgroundFloors = overgroundFloors;
        buildingAttributes.totalFloors = undergroundFloors + overgroundFloors;
        buildingAttributes.gfa = gfa;
        buildingAttributes.approvalDate = approvalDate;

        return buildingAttributes;
    }


    public static BuildingAttributesBuilder builder() {
        return new BuildingAttributesBuilder();
    }

    public static class BuildingAttributesBuilder {

        private BuildingAttributes attributes;

        private BuildingAttributesBuilder() {
            this.attributes = new BuildingAttributes();
        }

        public Map<String, Field> buildFields() {
            Map<String, Field> buildingFieldList = new ConcurrentHashMap<>();

            buildingFieldList.put("buildingHumanCapacity", new IntegerField("bhc", attributes.buildingHumanCapacity, Integer.class));
            buildingFieldList.put("buildingClassification", new IntegerField("bc", attributes.buildingClassification, Integer.class));
            buildingFieldList.put("buildingSpecification", new IntegerField("bs", attributes.buildingSpecification, Integer.class));
            buildingFieldList.put("buildingMaterial", new IntegerField("bm", attributes.buildingMaterial, Integer.class));
            buildingFieldList.put("undergroundFloors", new IntegerField("ugf", attributes.undergroundFloors, Integer.class));
            buildingFieldList.put("overgroundFloors", new IntegerField("ogf", attributes.overgroundFloors, Integer.class));
            buildingFieldList.put("totalFloors", new IntegerField("tf", attributes.undergroundFloors + attributes.overgroundFloors, Integer.class));
            buildingFieldList.put("GFA", new DoubleField("gfa", attributes.gfa, Double.class));
            buildingFieldList.put("dateOfApproval", new LocalDateField("approvalDate", attributes.approvalDate, LocalDate.class));

            return buildingFieldList;
        }

        public Building build() {
            Building building = new Building();
            building.setBuildingFieldMap(buildFields());
            return building;
        }
    }
}

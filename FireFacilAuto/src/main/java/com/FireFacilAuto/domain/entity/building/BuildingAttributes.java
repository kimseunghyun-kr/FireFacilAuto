package com.FireFacilAuto.domain.entity.building;

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

        public Map<String, Field<?>> buildFields() {
            Map<String, Field<?>> buildingFieldList = new ConcurrentHashMap<>();

            buildingFieldList.put("buildingHumanCapacity", new Field<>("bhc", attributes.buildingHumanCapacity, Integer.class));
            buildingFieldList.put("buildingClassification", new Field<>("bc", attributes.buildingClassification, Integer.class));
            buildingFieldList.put("buildingSpecification", new Field<>("bs", attributes.buildingSpecification, Integer.class));
            buildingFieldList.put("buildingMaterial", new Field<>("bm", attributes.buildingMaterial, Integer.class));
            buildingFieldList.put("undergroundFloors", new Field<>("ugf", attributes.undergroundFloors, Integer.class));
            buildingFieldList.put("overgroundFloors", new Field<>("ogf", attributes.overgroundFloors, Integer.class));
            buildingFieldList.put("totalFloors", new Field<>("tf", attributes.undergroundFloors + attributes.overgroundFloors, Integer.class));
            buildingFieldList.put("GFA", new Field<>("gfa", attributes.gfa, Double.class));
            buildingFieldList.put("dateOfApproval", new Field<>("approvalDate", attributes.approvalDate, LocalDate.class));

            return buildingFieldList;
        }

        public Building build() {
            Building building = new Building();
            building.setBuildingFieldMap(buildFields());
            return building;
        }
    }
}

package com.FireFacilAuto.domain.entity.floors;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Field;
import lombok.Data;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class FloorAttributes {

    public static class FloorBuilder {
        private Long UUID;
        private Building building;
        private Map<String, Field<?>> fieldValues = new ConcurrentHashMap<>();

        public FloorBuilder() {
        }

        public FloorBuilder UUID(Long UUID) {
            this.UUID = UUID;
            return this;
        }

        public FloorBuilder building(Building building) {
            this.building = building;
            return this;
        }

        public FloorBuilder floorNo(Integer floorNo) {
            fieldValues.put("floorNo", new Field<>("floorNo", floorNo, Integer.class));
            return this;
        }

        public FloorBuilder isUnderGround(Boolean isUnderGround) {
            fieldValues.put("isUnderGround", new Field<>("isUnderGround", isUnderGround, Boolean.class));
            return this;
        }

        public FloorBuilder floorClassification(Integer floorClassification) {
            fieldValues.put("floorClassification", new Field<>("floorClassification", floorClassification, Integer.class));
            return this;
        }

        public FloorBuilder floorSpecification(Integer floorSpecification) {
            fieldValues.put("floorSpecification", new Field<>("floorSpecification", floorSpecification, Integer.class));
            return this;
        }

        public FloorBuilder floorArea(Double floorArea) {
            fieldValues.put("floorArea", new Field<>("floorArea", floorArea, Double.class));
            return this;
        }

        public FloorBuilder floorMaterial(Integer floorMaterial) {
            fieldValues.put("floorMaterial", new Field<>("floorMaterial", floorMaterial, Integer.class));
            return this;
        }

        public FloorBuilder floorWindowAvailability(Boolean floorWindowAvailability) {
            fieldValues.put("floorWindowAvailability", new Field<>("floorWindowAvailability", floorWindowAvailability, Boolean.class));
            return this;
        }

        public Floor build() {
            Floor floor = new Floor();
            floor.setUUID(this.UUID);
            floor.setBuilding(this.building);
            floor.setFloorFieldMap(fieldValues);
            return floor;
        }
    }
}

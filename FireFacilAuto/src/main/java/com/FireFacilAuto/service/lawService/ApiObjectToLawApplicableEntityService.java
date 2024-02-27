package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Field;
import com.FireFacilAuto.domain.entity.building.PossibleBuildingFields;
import com.FireFacilAuto.domain.entity.floors.Floor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.FireFacilAuto.domain.entity.building.PossibleBuildingFields.getBuildingClass;
import static com.FireFacilAuto.domain.entity.floors.PossibleFloorFields.getFloorClass;

@Service
@Slf4j
public class ApiObjectToLawApplicableEntityService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    /**
     * Map TitleResponseItem information to initialize a Building object.
     *
     * @param address            Address object for the building.
     * @param titleResponseItem  TitleResponseItem containing building information.
     * @param building           Building object to be initialized.
     */
    public void buildingInitializr(Address address, TitleResponseItem titleResponseItem, Building building) {
        // TODO: allocate number for classification based on code
        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem);

        Field<Integer> buildingClassification = new Field<>("buildingClassification",classificationCodeMapper(titleResponseItem), PossibleBuildingFields.getBuildingClass("buildingClassification"));
        Field<Integer> buildingSpecification = new Field<>("buildingSpecification",specificationCodeMapper(titleResponseItem), PossibleBuildingFields.getBuildingClass("buildingSpecification"));

        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem.getPmsDay());
        Field<LocalDate> dateOfApproval = new Field<>("dateOfApproval",
                (titleResponseItem.getPmsDay().trim().isEmpty() ? LocalDate.now() : LocalDate.parse(titleResponseItem.getPmsDay(), formatter)),getBuildingClass("dateOfApproval"));

        Field<Double> GFA = new Field<>("GFA",Double.valueOf(titleResponseItem.getTotArea()), getBuildingClass("GFA"));
        Field<Integer> undergroundFloors = new Field<>("undergroundFloors",Integer.valueOf(titleResponseItem.getUgrndFlrCnt()),getBuildingClass("undergroundFloors"));
        Field<Integer> overgroundFloors = new Field<>("overgroundFloors",Integer.valueOf(titleResponseItem.getGrndFlrCnt()),getBuildingClass("overgroundFloors"));
        Field<Integer> totalFloors = new Field<>("totalFloors", undergroundFloors.value()+ overgroundFloors.value(),getBuildingClass("totalFloors"));
        Field<Integer> buildingMaterial = new Field<>("buildingMaterial", Integer.valueOf(titleResponseItem.getStrctCd()),getBuildingClass("buildingMaterial"));
        // TODO: calculate capacity

        Field<Integer> buildingHumanCapacity = new Field<>("buildingHumanCapacity", getBuildingCapacity(titleResponseItem), getBuildingClass("buildingHumanCapacity"));

        building.setJuso(address);
        building.setBuildingFieldList(List.of(buildingClassification,buildingSpecification,dateOfApproval
                ,GFA,undergroundFloors,overgroundFloors,totalFloors,buildingMaterial,buildingHumanCapacity));
    }

    /**
     * Map FloorResponseItem and ExposedInfoResponseItem information to initialize a Floor object.
     *
     * @param titleResponseItem    TitleResponseItem containing building information.
     * @param exposInfoResponseItem ExposedInfoResponseItem containing floor exposure information.
     * @param floorResponseItem     FloorResponseItem containing floor information.
     * @param floor                 Floor object to be initialized.
     */
    public void floorInitializr(TitleResponseItem titleResponseItem, ExposedInfoResponseItem exposInfoResponseItem,
                                 FloorResponseItem floorResponseItem, Floor floor) {
        Field<Integer> floorClassification = new Field<>("floorClassification",classificationCodeMapper(floorResponseItem, titleResponseItem), getFloorClass("floorClassification"));
        Field<Integer> floorSpecification = new Field<>("floorSpecification",specificationCodeMapper(floorResponseItem, titleResponseItem), getFloorClass("floorSpecification"));

        Field<Integer> floorMaterial = new Field<>("floorMaterial", Integer.valueOf(floorResponseItem.getStrctCd()), getFloorClass("floorMaterial"));
        Field<Double> floorArea = new Field<>("floorArea", Double.valueOf(floorResponseItem.getArea()), getFloorClass("floorArea"));

        Field<Boolean> isUnderGround;
        Field<Integer> floorNo;
        if (exposInfoResponseItem != null) {
            isUnderGround = new Field<>("isUnderGround", floorGbCdMapper(exposInfoResponseItem.getFlrGbCd()),getFloorClass("isUnderGround"));
            floorNo = new Field<>("floorNo", Integer.valueOf(exposInfoResponseItem.getFlrNo()),getFloorClass("floorNo"));
        } else {
            isUnderGround = new Field<>("isUnderGround", floorGbCdMapper(floorResponseItem.getFlrGbCd()),getFloorClass("isUnderGround"));
            floorNo = new Field<>("floorNo", Integer.valueOf(floorResponseItem.getFlrNo()),getFloorClass("floorNo"));
        }

        floor.setFloorFieldList(List.of(floorClassification,floorSpecification,floorArea,floorNo,floorMaterial,isUnderGround));
    }

    /**
     * Map floor classification based on FloorResponseItem and TitleResponseItem.
     *
     * @param floor    FloorResponseItem containing floor information.
     * @param title    TitleResponseItem containing building information.
     * @return Integer representing the floor classification.
     */
    private Integer classificationCodeMapper(FloorResponseItem floor, TitleResponseItem title) {
        return 1; // TODO: implement classification code mapping logic
    }

    /**
     * Map building classification based on TitleResponseItem.
     *
     * @param title    TitleResponseItem containing building information.
     * @return Integer representing the building classification.
     */
    private Integer classificationCodeMapper(TitleResponseItem title) {
        return 1; // TODO: implement classification code mapping logic
    }

    /**
     * Map floor specification based on FloorResponseItem and TitleResponseItem.
     *
     * @param floor    FloorResponseItem containing floor information.
     * @param title    TitleResponseItem containing building information.
     * @return Integer representing the floor specification.
     */
    private Integer specificationCodeMapper(FloorResponseItem floor, TitleResponseItem title) {
        return 1; // TODO: implement specification code mapping logic
    }

    /**
     * Map building specification based on TitleResponseItem.
     *
     * @param title    TitleResponseItem containing building information.
     * @return Integer representing the building specification.
     */
    private Integer specificationCodeMapper(TitleResponseItem title) {
        return 1; // TODO: implement specification code mapping logic
    }

    /**
     * Get the building capacity from TitleResponseItem.
     *
     * @param titleResponseItem TitleResponseItem containing building information.
     * @return Integer representing the building capacity.
     */
    private Integer getBuildingCapacity(TitleResponseItem titleResponseItem) {
        return Integer.valueOf(titleResponseItem.getHhldCnt()); // TODO: implement capacity calculation logic
    }

    /**
     * Map floor type code to determine if the floor is underground.
     *
     * @param flrGbCd Floor type code.
     * @return Boolean indicating if the floor is underground.
     */
    private Boolean floorGbCdMapper(String flrGbCd) {
        int target = Integer.parseInt(flrGbCd);
        if (target == 20 || target == 21 || target == 22 || target == 30) {
            return false;
        } else if (target == 10) {
            return true;
        } else if (target == 40 || target == 0) {
            log.info("target at {}", target);
            return false;
        }
        throw new RuntimeException("Invalid Mapping");
    }
}

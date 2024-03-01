package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.building.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


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
        Map<String,Field<?>> fields = BuildingAttributes.builder()
                .buildingClassification(classificationCodeMapper(titleResponseItem))
                .buildingSpecification(specificationCodeMapper(titleResponseItem))
                .gfa(Double.parseDouble(titleResponseItem.getTotArea()))
                .approvalDate(titleResponseItem.getPmsDay().trim().isEmpty() ? LocalDate.now() : LocalDate.parse(titleResponseItem.getPmsDay(), formatter))
                .buildingMaterial(Integer.parseInt(titleResponseItem.getStrctCd()))
                .buildingHumanCapacity(getBuildingCapacity(titleResponseItem))
                .overgroundFloors(Integer.parseInt(titleResponseItem.getGrndFlrCnt()))
                .undergroundFloors(Integer.parseInt(titleResponseItem.getUgrndFlrCnt()))
                .buildFields();


        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem.getPmsDay());
        // TODO: calculate capacity
        building.setJuso(address);
        building.setBuildingFieldMap(fields);
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
        FloorAttributes.FloorBuilder fb = new FloorAttributes.FloorBuilder()
                .floorClassification(classificationCodeMapper(floorResponseItem, titleResponseItem))
                .floorSpecification(specificationCodeMapper(floorResponseItem, titleResponseItem))
                .floorArea(Double.valueOf(floorResponseItem.getArea()))
                .floorMaterial(Integer.valueOf(floorResponseItem.getStrctCd()));


        if (exposInfoResponseItem != null) {
            fb = fb.floorWindowAvailability(floorGbCdMapper(exposInfoResponseItem.getFlrGbCd()))
                    .floorNo(Integer.valueOf(exposInfoResponseItem.getFlrNo()));
        } else {
            fb = fb.floorWindowAvailability(floorGbCdMapper(floorResponseItem.getFlrGbCd()))
                    .floorNo(Integer.valueOf(floorResponseItem.getFlrNo()));
        }

        Map<String,Field<?>> fields = fb.build().getFloorFieldMap();
        floor.setFloorFieldMap(fields);
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

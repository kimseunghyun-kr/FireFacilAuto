package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ApiObjectToLawApplicableEntityService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private Boolean floorGbCdMapper(String flrGbCd) {
        int target = Integer.parseInt(flrGbCd);
        if(target == 20 || target == 21 || target == 22 || target == 30) {
            return false;
        } else if (target == 10) {
            return true;
        } else if (target == 40 || target == 0) {
            log.info("target at {}", target);
            return false;
        }
        throw new RuntimeException("Invalid Mapping");
    }

    public void buildingInitializr(Address address, TitleResponseItem titleResponseItem, Building building) {
        //TODO -> allocate number for classification based on code
        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem);

        building.setBuildingClassification(classificationCodeMapper(titleResponseItem));
        building.setBuildingSpecification(specificationCodeMapper(titleResponseItem));

        building.setJuso(address);

        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem.getPmsDay());
        building.setDateofApproval(titleResponseItem.getPmsDay().trim().isEmpty() ? LocalDate.now() :LocalDate.parse(titleResponseItem.getPmsDay(),formatter));
        building.setGFA(Double.valueOf(titleResponseItem.getTotArea()));
        building.setUndergroundFloors(Integer.valueOf(titleResponseItem.getUgrndFlrCnt()));
        building.setOvergroundFloors(Integer.valueOf(titleResponseItem.getGrndFlrCnt()));
        building.setTotalFloors(building.overgroundFloors + building.undergroundFloors);
        //TODO -> calculate capacity
        building.setBuildingHumanCapacity(getBuildingCapacity(titleResponseItem));
    }


    public void floorInitializr(TitleResponseItem titleResponseItem, ExposedInfoResponseItem exposInfoResponseItem, FloorResponseItem floorResponseItem, Floor floor) {
        floor.setFloorClassification(classificationCodeMapper(floorResponseItem, titleResponseItem));
        floor.setFloorSpecification(specificationCodeMapper(floorResponseItem, titleResponseItem));
        floor.setFloorMaterial(Integer.valueOf(floorResponseItem.getStrctCd()));
        floor.setFloorArea(Double.valueOf(floorResponseItem.getArea()));
        if(exposInfoResponseItem != null) {
            floor.setIsUnderGround(floorGbCdMapper(exposInfoResponseItem.getFlrGbCd()));
            floor.setFloorNo(Integer.valueOf(exposInfoResponseItem.getFlrNo()));
        } else {
            floor.setIsUnderGround(floorGbCdMapper(floorResponseItem.getFlrGbCd()));
            floor.setFloorNo(Integer.valueOf(floorResponseItem.getFlrNo()));
        }
    }

    private Integer getBuildingCapacity(TitleResponseItem titleResponseItem) {
        return Integer.valueOf(titleResponseItem.getHhldCnt());
    }

    //TODO
    private Integer classificationCodeMapper(FloorResponseItem floor, TitleResponseItem title) {
        return 1;
    }
    //TODO
    private Integer classificationCodeMapper(TitleResponseItem title) {
        return 1;
    }

    //TODO
    private Integer specificationCodeMapper(FloorResponseItem floor, TitleResponseItem title) {
        return 1;
    }
    //TODO
    private Integer specificationCodeMapper(TitleResponseItem title) {
        return 1;
    }


}

package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawExecutionService;
import com.FireFacilAuto.service.lawService.floorLaws.FloorLawExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BuildingAndFloorLawExecutionFacadeService {

    private final BuildingLawExecutionService buildingLawExecutionService;
    private final FloorLawExecutionService floorLawExecutionService;
    private final ApiObjectToLawApplicableEntityService apiObjectConverter;


    public BuildingAndFloorLawExecutionFacadeService(BuildingLawExecutionService buildingLawExecutionService, FloorLawExecutionService floorLawExecutionService, ApiObjectToLawApplicableEntityService apiObjectConverter) {
        this.buildingLawExecutionService = buildingLawExecutionService;
        this.floorLawExecutionService = floorLawExecutionService;
        this.apiObjectConverter = apiObjectConverter;
    }

    public ResultSheet buildingBuildAndExecuteLaw(Address address, TitleResponseItem titleResponseItem, List<FloorResponseItem> floorResponseItems) {
        Building building = new Building();
        apiObjectConverter.buildingInitializr(address, titleResponseItem, building);

        List<Floor> floors = floorResponseItemListToFloorListConverter(titleResponseItem, floorResponseItems, building);
        building.setCompositeFloors(floors);

        return executeLaw(building);
    }



    private List<Floor> floorResponseItemListToFloorListConverter(TitleResponseItem titleResponseItem, List<FloorResponseItem> floorResponseItems, Building building) {
        return floorResponseItems.stream().map(floorResponseItem -> {
            Floor floor = new Floor();
            floor.setBuilding(building);

            //TODO -> allocate number for classification based on code
            //Classification, specififation;

            apiObjectConverter.floorInitializr(titleResponseItem,null, floorResponseItem, floor);
            return floor;
        }).toList();
    }



    public ResultSheet floorBuildAndExecuteLaw(Address address, TitleResponseItem titleResponseItem,
                                               ExposedInfoResponseItem exposInfoResponseItem,
                                               FloorResponseItem floorResponseItem) {
        Building building = new Building();
        apiObjectConverter.buildingInitializr(address, titleResponseItem, building);
        Floor floor = new Floor();
        apiObjectConverter.floorInitializr(titleResponseItem, exposInfoResponseItem, floorResponseItem, floor);
        building.setCompositeFloors(List.of(floor));
        return executeLaw(building);
    }


    public ResultSheet executeLaw(Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(building);

        List<FloorResults> floorResultsList = floorResultSheetBuilder(building);
        resultSheet.setFloorResultsList(floorResultsList);

        log.info("executing building laws");
        buildingLawExecutionService.buildingLawExecute(building, floorResultsList);

        log.info("executing floor laws");
        floorLawExecutionService.resolveFloorLawThenExecute(building, floorResultsList);

        return resultSheet;
    }

    private ResultSheet resultSheetInitializr(Building building) {
        ResultSheet resultSheet = new ResultSheet();
        resultSheet.setBuilding(building);
        return resultSheet;
    }
    private List<FloorResults> floorResultSheetBuilder(Building building) {
        return building.getCompositeFloors().stream().map(FloorResults::floorFactory).toList();
    }



}

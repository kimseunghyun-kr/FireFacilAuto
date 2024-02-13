package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.WebClient.api.APICollationService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints.BaseApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.floorEndpoint.FloorApiService;
import com.FireFacilAuto.service.buildingService.BuildingService;
import com.FireFacilAuto.service.lawService.BuildingLawExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes({"response", "address"})
public class Main {
    private final FloorApiService floorApiService;
    private final BaseApiService baseApiService;



    private final BuildingLawExecutionService buildingLawExecutionService;
    private final BuildingService buildingService;
    private final APICollationService apiCollationService;



    @Autowired
    public Main(FloorApiService floorApiService, BaseApiService baseApiService, BuildingLawExecutionService buildingLawExecutionService, BuildingService buildingService, APICollationService apiCollationService) {
        this.floorApiService = floorApiService;
        this.baseApiService = baseApiService;
        this.buildingLawExecutionService = buildingLawExecutionService;
        this.buildingService = buildingService;
        this.apiCollationService = apiCollationService;
    }

    @GetMapping("/input")
    public String addressForm(Model model) {
        model.addAttribute("address", new Address());
        return "main/addressForm";
    }

    @PostMapping("/baseSelect")
    public String baseSelectModelPopulateSession (Model model, @ModelAttribute Address address) {
        List<BaseResponseItem> resultList =  baseApiService.fetchAllBaseData(address, "getBrBasisOulnInfo");
        log.info("responseBody, {}", resultList);
        buildingService.process(resultList, address);
        model.addAttribute("response", resultList);
        model.addAttribute("address", address);
        return "redirect:/main/baseInformationDetails";
    }

    @GetMapping("/baseInformationDetails")
    public String baseSelectFormShow (Model model) {
        // The data is retrieved from the session attribute
        List<BaseResponseItem> resultList = (List<BaseResponseItem>) model.getAttribute("response");
        // Populate model attributes if needed
        model.addAttribute("baseResponseItem", resultList);
        return "/main/baseInformationDetails";
    }

    @PostMapping("/submitBaseObject")
    public String baseSelectedItemReceive (Model model, @ModelAttribute BaseResponseItem baseResponseItemObject) {
        apiCollationService.process(baseResponseItemObject, (Address) model.getAttribute("address"));
        return "redirect:/main/showCollated";
    }

    @GetMapping("/execute")
    public String showExecutedResults(Model model) {
        Building building = new Building();

        ResultSheet resultSheet = buildingLawExecutionService.executeLaw(building);

        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);
        return "baseInformationDetails";
    }


}

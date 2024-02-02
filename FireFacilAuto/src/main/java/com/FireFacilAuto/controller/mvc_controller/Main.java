package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.api.ApiResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.WebClient.WebClientApiService;
import com.FireFacilAuto.service.buildingService.BuildingService;
import com.FireFacilAuto.service.lawService.BuildingLawExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes("response")
public class Main {
    private final WebClientApiService apiService;
    private final BuildingLawExecutionService buildingLawExecutionService;
    private final BuildingService buildingService;

    @Autowired
    public Main(WebClientApiService apiService, BuildingLawExecutionService buildingLawExecutionService, BuildingService buildingService) {
        this.apiService = apiService;
        this.buildingLawExecutionService = buildingLawExecutionService;
        this.buildingService = buildingService;
    }

    @GetMapping("/input")
    public String addressForm(Model model) {
        model.addAttribute("address", new Address());
        return "main/addressForm";
    }

    @PostMapping("/informationDetails")
    public String rawResultShow(Model model, Address address) {
        List<ApiResponseItem> resultList = apiService.fetchAllData(address);
        buildingService.process(resultList);
        log.info("responseBody, {}", resultList);
        model.addAttribute("response", resultList);
        return "redirect:/main/informationDetails";
    }

    @GetMapping("/informationDetails")
    public String showInformationDetails(Model model) {

        // The data is retrieved from the session attribute
        List<ApiResponseItem> resultList = (List<ApiResponseItem>) model.getAttribute("response");

        // Populate model attributes if needed
        model.addAttribute("response", resultList);
        return "main/informationDetails";
    }

    @GetMapping("/execute")
    public String showExecutedResults(Model model) {
        Building building = new Building();

        ResultSheet resultSheet = buildingLawExecutionService.executeLaw(building);

        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);
        return "main/informationDetails";
    }


}

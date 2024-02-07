package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.WebClient.WebClientApiService;
import com.FireFacilAuto.service.WebClient.apiEndpoints.baseEndpoints.BaseApiService;
import com.FireFacilAuto.service.WebClient.apiEndpoints.floorEndpoint.FloorApiService;
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
@SessionAttributes("response")
public class Main {
    private final WebClientApiService apiService;
    private final FloorApiService floorApiService;
    private final BaseApiService baseApiService;
    private final BuildingLawExecutionService buildingLawExecutionService;
    private final BuildingService buildingService;



    @Autowired
    public Main(WebClientApiService apiService, FloorApiService floorApiService, BaseApiService baseApiService, BuildingLawExecutionService buildingLawExecutionService, BuildingService buildingService) {
        this.apiService = apiService;
        this.floorApiService = floorApiService;
        this.baseApiService = baseApiService;
        this.buildingLawExecutionService = buildingLawExecutionService;
        this.buildingService = buildingService;
    }

    @GetMapping("/input")
    public String addressForm(Model model) {
        model.addAttribute("address", new Address());
        return "main/addressForm";
    }

    @PostMapping("/baseSelect")
    public String rawResultShow(Model model, @ModelAttribute Address address) {
        List<BaseResponseItem> resultList =  baseApiService.fetchAllBaseData(address, "getBrBasisOulnInfo");
//        buildingService.process(resultList, address);
        log.info("responseBody, {}", resultList);
        model.addAttribute("response", resultList);
        return "redirect:/main/baseInformationDetails";
    }

    @GetMapping("/baseInformationDetails")
    public String showInformationDetails(Model model) {

        // The data is retrieved from the session attribute
        List<BaseResponseItem> resultList = (List<BaseResponseItem>) model.getAttribute("response");

        // Populate model attributes if needed
        model.addAttribute("baseResponseItem", resultList);
        return "/main/baseInformationDetails";
    }

    @GetMapping("/execute")
    public String showExecutedResults(Model model) {
        Building building = new Building();

        ResultSheet resultSheet = buildingLawExecutionService.executeLaw(building);

        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);
        return "baseInformationDetails";
    }

//    @PostMapping("/informationDetails")
//    public String testRawResultShow(RedirectAttributes redirectAttributes, @ModelAttribute Address address, @RequestParam(name="requestType") String requestType) {
//        Map<String, Object> response = apiService.fetchSingleData(address, requestType);
//        redirectAttributes.addFlashAttribute("response", response);
//        return "redirect:/main/informationDetails";
//    }
//
//    @GetMapping("/informationDetails")
//    public String testInformationDetails(@ModelAttribute("response") Map<String, Object> response, Model model) {
//        // Populate model attributes if needed
//        model.addAttribute("response", response);
//        return "main/informationDetails";
//    }



}

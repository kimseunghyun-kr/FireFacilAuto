package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main")
@SessionAttributes({"baseResponseItem", "address"})
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
    public String baseSelectModelPopulateSession (RedirectAttributes redirectAttributes, Model model, @ModelAttribute Address address) {
        List<BaseResponseItem> resultList =  apiCollationService.concurrentPreGetFromApi(address);
        log.info("responseBody, {}", resultList);
        buildingService.process(resultList, address);
        redirectAttributes.addAttribute("response", resultList);
        model.addAttribute("address", address);
        return "redirect:/main/baseInformationDetails";
    }

    @GetMapping("/baseInformationDetails")
    public String baseSelectFormShow (RedirectAttributes redirectAttributes, Model model) {
        // The data is retrieved from the session attribute
        List<BaseResponseItem> resultList = (List<BaseResponseItem>) redirectAttributes.getAttribute("response");
        // Populate model attributes if needed
        model.addAttribute("baseInfoList", resultList);
        return "/main/baseInformationDetails";
    }

    @PostMapping("/submitBaseObject")
    public String baseSelectItemReceiveAndFindExposInfo (RedirectAttributes redirectAttributes, Model model, @ModelAttribute BaseResponseItem baseResponseItemObject) {
        model.addAttribute("baseResponseItem",baseResponseItemObject);
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = apiCollationService.getfurtherSpecificSelect(baseResponseItemObject, (Address) model.getAttribute("address"));
        redirectAttributes.addAttribute("exposInfoList", exposedInfoResponseItemList);
        return "redirect:/main/showExpos";
    }

    @GetMapping("/showExpos")
    public String exposSelectFormShow (Model model, RedirectAttributes redirectAttributes) {
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = (List<ExposedInfoResponseItem>) redirectAttributes.getAttribute("exposInfoList");
        assert exposedInfoResponseItemList != null;
        if(exposedInfoResponseItemList.isEmpty()) {
            return "redirect:/main/continueWithTitleObj"; // to modify later into process.
        }
        model.addAttribute("exposInfoList", exposedInfoResponseItemList);
        return "/main/exposInformationDetails";
    }

    @PostMapping("/continueWithExposInfoObj")
    public String continueWithExposInfoObj (Model model, @ModelAttribute ExposedInfoResponseItem exposedInfoResponseItem){
        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase((BaseResponseItem) model.getAttribute("baseResponseItem"), (Address) model.getAttribute("address"));

        //Todo

        return "/main/input";
    }

    @PostMapping("/continueWithTitleObj")
    public String continueWithTitleObj (Model model) {
        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase((BaseResponseItem) model.getAttribute("baseResponseItem"), (Address) model.getAttribute("address"));

        //Todo

        return "/main/input";
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

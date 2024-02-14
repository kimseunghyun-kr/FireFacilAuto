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
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main")
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
    public String baseSelectModelPopulateSession (HttpSession httpSession, Model model, @ModelAttribute Address address) {
        List<List<BaseResponseItem>> collatedList =  apiCollationService.concurrentPreGetFromApi(address);
        if(collatedList.size() != 2) {
            return "redirect:/main/input";
        }
        List<BaseResponseItem> baseTitleList = collatedList.getFirst();
        List<BaseResponseItem> baseExposList = collatedList.getLast();
        log.info("responseBody, {}", baseTitleList);
//        buildingService.process(baseTitleList, address);

        httpSession.setAttribute("baseExposList", baseExposList);
        httpSession.setAttribute("response", baseTitleList);
        httpSession.setAttribute("address", address);

        model.addAttribute("address", address);
        return "redirect:/main/baseInformationDetails";
    }

    @GetMapping("/baseInformationDetails")
    public String baseSelectFormShow (HttpSession httpSession, Model model) {
        // get list from session attributes
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>) httpSession.getAttribute("response");
        // Populate model attributes if needed
        model.addAttribute("baseInfoList", baseResponseItemList);
        return "/main/baseInformationDetails";
    }

    @PostMapping("/submitBaseObject")
    public String baseSelectItemReceiveAndFindExposInfo (HttpSession httpSession, Model model, @ModelAttribute("baseResponsePk") String baseResponsePk) {
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>)httpSession.getAttribute("response");
        log.info("baseItemResponseListCheck at submitBaseObject, {}", baseResponseItemList);
        BaseResponseItem baseResponseItem = baseResponseItemList.stream().filter(obj -> obj.getMgmBldrgstPk().equals(baseResponsePk)).findFirst().orElseThrow();

        httpSession.removeAttribute("response");
        httpSession.setAttribute("baseResponseItem", baseResponseItem);

        log.info("controllerBaseResponseItem check at baseSelectItemAndFindExposInfo {}, " ,baseResponseItem);
        List<BaseResponseItem> baseExposList = (List<BaseResponseItem>) httpSession.getAttribute("baseExposList");
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = apiCollationService.getfurtherSpecificSelect(baseResponseItem, baseExposList, (Address) httpSession.getAttribute("address"));

        httpSession.setAttribute("exposInfoList", exposedInfoResponseItemList);
        return "redirect:/main/showExpos";
    }

    @GetMapping("/showExpos")
    public String exposSelectFormShow (Model model, HttpSession httpSession) {
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = (List<ExposedInfoResponseItem>) httpSession.getAttribute("exposInfoList");
        assert exposedInfoResponseItemList != null;
        log.info("exposInfo check at exposFormshow {}, " , httpSession.getAttribute("exposInfoList"));
        if(exposedInfoResponseItemList.isEmpty()) {
            return "redirect:/main/continueWithTitleObj"; // to modify later into process.
        }
        model.addAttribute("exposInfoList", exposedInfoResponseItemList);
        return "/main/exposInformationDetails";
    }

    @PostMapping("/continueWithExposInfoObj")
    public String continueWithExposInfoObj (Model model, HttpSession httpSession){
        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase((BaseResponseItem) httpSession.getAttribute("baseResponseItem"), (Address) httpSession.getAttribute("address"));

        //Todo

        return "redirect:/main/input";
    }

    @PostMapping("/continueWithTitleObj")
    public String continueWithTitleObj (Model model, HttpSession httpSession) {
        BaseResponseItem baseResponseItem = (BaseResponseItem) httpSession.getAttribute("baseResponseItem");
        log.info("controllerBaseResponseItem check at continueWIthTitleObj {}, " ,baseResponseItem);
        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase((BaseResponseItem) httpSession.getAttribute("baseResponseItem"), (Address) httpSession.getAttribute("address"));

        //Todo

        return "redirect:/main/input";
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

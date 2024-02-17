package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
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

    @PostMapping("/input")
    public String redirectToAddressForm(Model model) {
        return "redirect:/main/input";
    }
    @GetMapping("/input")
    public String addressForm(Model model) {
        model.addAttribute("address", new Address());
        return "main/addressForm";
    }

    @PostMapping("/baseSelect")
    public String baseSelectModelPopulateSession (HttpSession httpSession, @ModelAttribute Address address) {
        List<List<BaseResponseItem>> collatedList =  apiCollationService.concurrentPreGetFromApi(address);
        if(collatedList.size() == 1) {
            httpSession.setAttribute("nonStandardBaseResponse", collatedList.getFirst());
            return "redirect:/main/nonStandardBaseInformationDetails";
        }
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

        return "redirect:/main/baseInformationDetails";
    }

    @GetMapping("/nonStandardBaseInformationDetails")
    public String baseSelecFormShowForNonStandard(HttpSession httpSession, Model model) {
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>) httpSession.getAttribute("nonStandardBaseResponse");
        if(baseResponseItemList.isEmpty()) {
            log.warn("completely empty . no matching results");
            return "redirect:/main/input";
        }

        model.addAttribute("baseInfoList", baseResponseItemList);
        model.addAttribute("flag", false);

        return "/main/baseInformationDetails";
    }

    @GetMapping("/baseInformationDetails")
    public String baseSelectFormShow (HttpSession httpSession, Model model) {
        // get list from session attributes
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>) httpSession.getAttribute("response");

        // Populate model attributes if needed
        model.addAttribute("baseInfoList", baseResponseItemList);
        model.addAttribute("flag", true);

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
            return "redirect:/main/floorDetails"; // to modify later into process.
        }
        model.addAttribute("exposInfoList", exposedInfoResponseItemList);
        return "/main/exposInformationDetails";
    }

    @PostMapping("/submitExposInfoObj")
    public String continueWithExposInfoObj (Model model, HttpSession httpSession, @ModelAttribute("exposInfoPk") String exposInfoPk){
        log.info("exposInfoPk, {}", exposInfoPk);
        List<ExposedInfoResponseItem> exposInfoList = (List<ExposedInfoResponseItem>) httpSession.getAttribute("exposInfoList");
        ExposedInfoResponseItem exposedInfoResponseItem = exposInfoList.stream().filter(exposObj -> exposObj.mgmBldrgstPk.equals(exposInfoPk)).findFirst().orElseThrow();
        httpSession.removeAttribute("exposInfoList");
        log.info("exposInfoResponseItem {}", exposedInfoResponseItem);
        httpSession.setAttribute("selectedExposItem", exposedInfoResponseItem);

        return "redirect:/main/exposTitleDetails";
    }

    @PostMapping("/continueWithTitleObj")
    public String continueWithTitleObj (Model model, HttpSession httpSession) {
        return "redirect:/main/floorDetails";
    }

    @GetMapping("/floorDetails")
    public String showFloorResults(HttpSession httpSession, Model model) {
        BaseResponseItem baseResponseItem = (BaseResponseItem) httpSession.getAttribute("baseResponseItem");
        Address address = (Address) httpSession.getAttribute("address");

        log.info("controllerBaseResponseItem check at continueWIthTitleObj {}, " ,baseResponseItem);

        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase(baseResponseItem, address);
        httpSession.setAttribute("titleResponseItem", titleResponseItem);

        List<FloorResponseItem> relatedFloorItems = apiCollationService.getFloorItemFromTitle(titleResponseItem, address);
        httpSession.setAttribute("floorResponseItemList", relatedFloorItems);

        model.addAttribute("titleResponseItem", titleResponseItem);
        model.addAttribute("floorInfoList", relatedFloorItems);
        return "/main/floorDetails";
    }

    @GetMapping("/exposTitleDetails")
    public String showSelectedTitleExposResult(HttpSession httpSession, Model model) {
        BaseResponseItem baseResponseItem = (BaseResponseItem) httpSession.getAttribute("baseResponseItem");
        Address address = (Address) httpSession.getAttribute("address");

        log.info("controllerBaseResponseItem check at continueWIthTitleObj {}, " ,baseResponseItem);
        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase(baseResponseItem, address);

        httpSession.setAttribute("titleResponseItem", titleResponseItem);

        ExposedInfoResponseItem exposedInfoResponseItem = (ExposedInfoResponseItem) httpSession.getAttribute("selectedExposItem");
        log.info("exposedInfoResponseItemAtshowselectedTitleExposResult , {}", exposedInfoResponseItem);

        model.addAttribute("titleResponseItem", titleResponseItem);
        model.addAttribute("exposInfoResponseItem", exposedInfoResponseItem);

        return "/main/exposTitleDetails";
    }


    @SuppressWarnings("unchecked")
    @GetMapping("/execute/BuildingFloor")
    public String TitleFloorExecutedresults (HttpSession httpSession, Model model) {
        TitleResponseItem titleResponseItem = (TitleResponseItem) httpSession.getAttribute("titleResponseItem");
        List<FloorResponseItem> floorResponseItems = (List<FloorResponseItem>) httpSession.getAttribute("floorResponseItemList");
        Address address = (Address)httpSession.getAttribute("address");

        ResultSheet resultSheet = buildingLawExecutionService.buildingBuildAndExecuteLaw(address, titleResponseItem, floorResponseItems);

        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);

        //Todo
        return "redirect:/main/input";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/execute/BuildingExposInfo")
    public String ExposInfoExecutedResults (HttpSession httpSession, Model model) {
        TitleResponseItem titleResponseItem = (TitleResponseItem) httpSession.getAttribute("titleResponseItem");
        ExposedInfoResponseItem exposInfoResponseItem = (ExposedInfoResponseItem) httpSession.getAttribute("selectedExposItem");
        Address address = (Address)httpSession.getAttribute("address");
        FloorResponseItem floorResponseItem = apiCollationService.getFloorItemFromTitle(titleResponseItem, address)
                .stream()
                .filter(item -> Integer.parseInt(item.flrGbCd) == Integer.parseInt(exposInfoResponseItem.flrGbCd) &&
                        Integer.parseInt(item.flrNo) == Integer.parseInt(exposInfoResponseItem.flrNo)).findFirst().orElseThrow();
        ResultSheet resultSheet = buildingLawExecutionService.floorBuildAndExecuteLaw(address, titleResponseItem, exposInfoResponseItem, floorResponseItem);

        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);

        //Todo
        return "redirect:/main/input";
    }


}

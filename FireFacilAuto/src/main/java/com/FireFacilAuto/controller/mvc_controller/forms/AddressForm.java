package com.FireFacilAuto.controller.mvc_controller.forms;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.WebClient.api.APICollationService;
import com.FireFacilAuto.service.lawService.BuildingAndFloorLawExecutionFacadeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/main/addressForm")
@Slf4j
public class AddressForm {
    private final BuildingAndFloorLawExecutionFacadeService lawExecutionFacadeService;
    private final APICollationService apiCollationService;

    @Autowired
    public AddressForm(BuildingAndFloorLawExecutionFacadeService lawExecutionFacadeService, APICollationService apiCollationService) {
        this.lawExecutionFacadeService = lawExecutionFacadeService;
        this.apiCollationService = apiCollationService;
    }

    @PostMapping("/input")
    public String redirectToAddressForm() {
        return "redirect:/main/addressForm/input";
    }
    @GetMapping("/input")
    public String addressForm(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("address", new Address());
        return "main/addressForm/addressForm";
    }

    @PostMapping("/baseSelect")
    public String baseSelectModelPopulateHttpSession (HttpSession session, @ModelAttribute Address address) {
        log.info("address at : {}", address);
        List<List<BaseResponseItem>> collatedList =  apiCollationService.concurrentPreGetFromApi(address);
        if(collatedList.size() == 1) {
            session.setAttribute("nonStandardBaseResponse", collatedList.getFirst());
            return "redirect:/main/addressForm/nonStandardBaseInformationDetails";
        }
        if(collatedList.size() != 2) {
            return "redirect:/main/addressForm/input";
        }
        List<BaseResponseItem> baseTitleList = collatedList.getFirst();
        List<BaseResponseItem> baseExposList = collatedList.getLast();
        log.debug("responseBody, {}", baseTitleList);
//        buildingService.process(baseTitleList, address);

        session.setAttribute("baseExposList", baseExposList);
        session.setAttribute("response", baseTitleList);
        session.setAttribute("address", address);

        return "redirect:/main/addressForm/baseInformationDetails";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/nonStandardBaseInformationDetails")
    public String baseSelecFormShowForNonStandard(HttpSession session, Model model) {
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>) session.getAttribute("nonStandardBaseResponse");
        if(baseResponseItemList.isEmpty()) {
            log.warn("completely empty . no matching results");
            return "redirect:/main/addressForm/input";
        }

        model.addAttribute("baseInfoList", baseResponseItemList);
        model.addAttribute("flag", false);

        return "main/addressForm/baseInformationDetails";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/baseInformationDetails")
    public String baseSelectFormShow (HttpSession session, Model model) {
        // get list from session attributes
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>) session.getAttribute("response");

        // Populate model attributes if needed
        model.addAttribute("baseInfoList", baseResponseItemList);
        model.addAttribute("flag", true);

        return "main/addressForm/baseInformationDetails";
    }

    @GetMapping("/submitBaseObject")
    public String redirectTobaseSelect() {
        return "redirect:/main/addressForm/input";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/submitBaseObject")
    public String baseSelectItemReceiveAndFindExposInfo (HttpSession session, @ModelAttribute("baseResponsePk") String baseResponsePk) {
        List<BaseResponseItem> baseResponseItemList = (List<BaseResponseItem>)session.getAttribute("response");
        log.debug("baseItemResponseListCheck at submitBaseObject, {}", baseResponseItemList);
        BaseResponseItem baseResponseItem = baseResponseItemList.stream().filter(obj -> obj.getMgmBldrgstPk().equals(baseResponsePk)).findFirst().orElseThrow();

        session.removeAttribute("response");
        session.setAttribute("baseResponseItem", baseResponseItem);

        log.debug("controllerBaseResponseItem check at baseSelectItemAndFindExposInfo {}, " ,baseResponseItem);
        List<BaseResponseItem> baseExposList = (List<BaseResponseItem>) session.getAttribute("baseExposList");
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = apiCollationService.getfurtherSpecificSelect(baseResponseItem, baseExposList, (Address) session.getAttribute("address"));

        session.setAttribute("exposInfoList", exposedInfoResponseItemList);
        return "redirect:/main/addressForm/showExpos";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/showExpos")
    public String exposSelectFormShow (Model model, HttpSession session) {
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = (List<ExposedInfoResponseItem>) session.getAttribute("exposInfoList");
        assert exposedInfoResponseItemList != null;
        log.debug("exposInfo check at exposFormshow {}", exposedInfoResponseItemList);
        if(exposedInfoResponseItemList.isEmpty()) {
            return "redirect:/main/addressForm/floorDetails"; // to modify later into process.
        }
        model.addAttribute("exposInfoList", exposedInfoResponseItemList);
        return "main/addressForm/exposInformationDetails";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/submitExposInfoObj")
    public String continueWithExposInfoObj (HttpSession session, @ModelAttribute("exposInfoPk") String exposInfoPk){
        log.debug("exposInfoPk, {}", exposInfoPk);
        List<ExposedInfoResponseItem> exposInfoList = (List<ExposedInfoResponseItem>) session.getAttribute("exposInfoList");
        ExposedInfoResponseItem exposedInfoResponseItem = exposInfoList.stream().filter(exposObj -> exposObj.mgmBldrgstPk.equals(exposInfoPk)).findFirst().orElseThrow();
        session.removeAttribute("exposInfoList");
        log.debug("exposInfoResponseItem {}", exposedInfoResponseItem);
        session.setAttribute("selectedExposItem", exposedInfoResponseItem);

        return "redirect:/main/addressForm/exposTitleDetails";
    }

    @PostMapping("/continueWithTitleObj")
    public String continueWithTitleObj () {
        return "redirect:/main/addressForm/floorDetails";
    }

    @GetMapping("/floorDetails")
    public String showFloorResults(HttpSession session, Model model) {
        BaseResponseItem baseResponseItem = (BaseResponseItem) session.getAttribute("baseResponseItem");
        Address address = (Address) session.getAttribute("address");

        log.debug("controllerBaseResponseItem check at continueWIthTitleObj {}, " ,baseResponseItem);

        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase(baseResponseItem, address);
        session.setAttribute("titleResponseItem", titleResponseItem);

        List<FloorResponseItem> relatedFloorItems = apiCollationService.getFloorItemFromTitle(titleResponseItem, address);
        session.setAttribute("floorResponseItemList", relatedFloorItems);

        model.addAttribute("titleResponseItem", titleResponseItem);
        model.addAttribute("floorInfoList", relatedFloorItems);
        return "main/addressForm/floorDetails";
    }

    @GetMapping("/exposTitleDetails")
    public String showSelectedTitleExposResult(HttpSession session, Model model) {
        BaseResponseItem baseResponseItem = (BaseResponseItem) session.getAttribute("baseResponseItem");
        Address address = (Address) session.getAttribute("address");

        log.debug("controllerBaseResponseItem check at continueWIthTitleObj {}, " ,baseResponseItem);
        TitleResponseItem titleResponseItem = apiCollationService.getTitleItemFromBase(baseResponseItem, address);

        session.setAttribute("titleResponseItem", titleResponseItem);

        ExposedInfoResponseItem exposedInfoResponseItem = (ExposedInfoResponseItem) session.getAttribute("selectedExposItem");
        log.debug("exposedInfoResponseItemAtshowselectedTitleExposResult , {}", exposedInfoResponseItem);

        model.addAttribute("titleResponseItem", titleResponseItem);
        model.addAttribute("exposInfoResponseItem", exposedInfoResponseItem);

        return "main/addressForm/exposTitleDetails";
    }


    @SuppressWarnings("unchecked")
    @PostMapping("/execute/BuildingFloor")
    public String TitleFloorExecutedresults (HttpSession session, Model model) {
        TitleResponseItem titleResponseItem = (TitleResponseItem) session.getAttribute("titleResponseItem");
        List<FloorResponseItem> floorResponseItems = (List<FloorResponseItem>) session.getAttribute("floorResponseItemList");
        Address address = (Address)session.getAttribute("address");

        ResultSheet resultSheet = lawExecutionFacadeService.BuildFromApiThenExecuteLaw(address, titleResponseItem, floorResponseItems);
        log.info("resultSheet, {}", resultSheet);
        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);

        //Todo
        return "redirect:/main/addressForm/input";
    }

    @PostMapping("/execute/BuildingExposInfo")
    public String ExposInfoExecutedResults (HttpSession session, Model model) {
        TitleResponseItem titleResponseItem = (TitleResponseItem) session.getAttribute("titleResponseItem");
        ExposedInfoResponseItem exposInfoResponseItem = (ExposedInfoResponseItem) session.getAttribute("selectedExposItem");
        Address address = (Address)session.getAttribute("address");
        FloorResponseItem floorResponseItem = apiCollationService.getFloorItemFromTitleForExpos(exposInfoResponseItem, titleResponseItem, address);
        ResultSheet resultSheet = lawExecutionFacadeService.BuildfromExposInfoItemThenExecute(address, titleResponseItem, exposInfoResponseItem, floorResponseItem);


        log.info("resultSheet, {}", resultSheet);

        // Populate model attributes if needed
        model.addAttribute("resultSheet", resultSheet);

        //Todo
        return "redirect:/main/addressForm/input";
    }
}

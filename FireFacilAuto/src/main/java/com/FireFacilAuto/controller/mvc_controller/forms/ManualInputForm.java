package com.FireFacilAuto.controller.mvc_controller.forms;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.DTO.form.FormFloorDTO;
import com.FireFacilAuto.domain.DTO.form.FormFloorDTOWrapper;
import com.FireFacilAuto.domain.configImport.ClassificationList;
import com.FireFacilAuto.domain.configImport.Specification;
import com.FireFacilAuto.service.formResolver.FormResolverService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main/manualInput")
public class ManualInputForm {
    private final ClassificationList classificationList;
    private final FormResolverService formResolverService;

    @Autowired
    public ManualInputForm(ClassificationList classificationList, FormResolverService formResolverService) {
        this.classificationList = classificationList;
        this.formResolverService = formResolverService;
    }

    @GetMapping("/input")
    public String showForm(Model model) {
        log.info("classificationList: {}", classificationList.getClassifications());
        model.addAttribute("input", new FormBuildingDTO());
        model.addAttribute("ClassificationConfig", classificationList);
        return "main/manualInput/inputform";
    }

    @GetMapping("/getSpecificationOptions")
    @ResponseBody
    public List<Specification> getSpecificationDropDownOptions(@RequestParam("classificationValue") String classificationValue) {
        log.info("valueselected : {}", classificationValue);
        int selectionValue = Integer.parseInt(classificationValue);
        assert (selectionValue > 0);
        return classificationList.getClassifications().get(selectionValue - 1).getSpecifications();
    }

    @GetMapping("/input/floors")
    public String buildFloors(HttpSession httpSession, Model model) {
        FormBuildingDTO buildTarget = (FormBuildingDTO) httpSession.getAttribute("buildTargetInfo");
        model.addAttribute("buildingInfo", buildTarget);
        FormFloorDTOWrapper dtoWrapper = buildFloorFormDTO(buildTarget);
        model.addAttribute("floorTargets", dtoWrapper);
        httpSession.setAttribute("floorDTOs", dtoWrapper.listWrapper);
        return "main/manualInput/floorInputForm";
    }

    private FormFloorDTOWrapper buildFloorFormDTO(FormBuildingDTO buildTarget) {
        FormFloorDTOWrapper dtoWrapper = new FormFloorDTOWrapper();
        List<FormFloorDTO> floorDTOList = new LinkedList<>();
        int undergroundCounter = 1;
        for (int i = 0; i < buildTarget.floor; i++) {
            FormFloorDTO floorDTO = new FormFloorDTO();
            if (i < buildTarget.undergroundFloors) {
                floorDTO.isUnderground = true;
                floorDTO.floorNo = undergroundCounter;
                undergroundCounter++;
            } else {
                floorDTO.floorNo = i + 1;
                floorDTO.isUnderground = false;
            }
            floorDTO.floorClassification = buildTarget.classification;
            floorDTO.floorSpecification = buildTarget.specification;
            floorDTOList.add(floorDTO);
        }
        dtoWrapper.listWrapper = floorDTOList;
        return dtoWrapper;
    }

    @PostMapping("/submitBuilding")
    public String submitFormBuilding(HttpSession httpSession, @ModelAttribute FormBuildingDTO inputDTO, Model model) {
        httpSession.setAttribute("buildTargetInfo", inputDTO);
        // Redirect or show success page
        return "redirect:/main/manualInput/input/floors";
    }

    @PostMapping("/processSelectedFloors")
    public ModelAndView processSelectedFloors(HttpSession session, @RequestParam("selectedFloors") List<Integer> selectedFloors, Model model) {
        FormBuildingDTO buildTarget = (FormBuildingDTO) session.getAttribute("buildTargetInfo");
        model.addAttribute("buildingInfo", buildTarget);
        FormFloorDTOWrapper dtoWrapper = buildFloorFormDTO(buildTarget);
        model.addAttribute("selectedFloors", model.addAttribute("selectedFloor", selectedFloors.stream().map(index -> dtoWrapper.getListWrapper().get(index)).toList()));
        return new ModelAndView("main/manualInput/floorInputForm");

    }

    @GetMapping("/loadSingleFloorContent")
    public String loadSingleFloorContent(HttpSession session, @RequestParam("selectedFloor") Integer selectedFloor, Model model) {
        FormBuildingDTO buildTarget = (FormBuildingDTO) session.getAttribute("buildTargetInfo");
        model.addAttribute("buildingInfo", buildTarget);
        FormFloorDTOWrapper dtoWrapper = buildFloorFormDTO(buildTarget);
        // Load content for a single selected floor
        model.addAttribute("selectedFloor", dtoWrapper.listWrapper.get(selectedFloor));
        return "main/manualInput/floorInputForm";
    }
    @PostMapping("/submitFloors")
    public String submitFormFloors(HttpSession httpSession, @ModelAttribute FormBuildingDTO inputDTO, Model model) {
        httpSession.setAttribute("buildTargetInfo", inputDTO);
        // Redirect or show success page
        return "redirect:/main/manualInput/input/floors";
    }





}




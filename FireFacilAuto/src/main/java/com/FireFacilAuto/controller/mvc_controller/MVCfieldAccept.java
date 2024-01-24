package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.BuildingClassificationConfig;
import com.FireFacilAuto.domain.DTO.InputDTO;
import com.FireFacilAuto.domain.entity.BuildTarget;
import com.FireFacilAuto.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/main")
public class MVCfieldAccept {
    private final BuildingClassificationConfig buildingClassificationConfig;
    private final MainService mainService;

    @Autowired
    public MVCfieldAccept(BuildingClassificationConfig buildingClassificationConfig, MainService mainService) {
        this.buildingClassificationConfig = buildingClassificationConfig;
        this.mainService = mainService;
    }

    @GetMapping("/input")
    public String showForm(Model model) {
        log.info(buildingClassificationConfig.getClassifications().toString());
        model.addAttribute("input", new InputDTO());
        model.addAttribute("ClassificationConfig", buildingClassificationConfig);
        return "inputform";
    }

//    @GetMapping("/getSpecificationOptions")
//    @ResponseBody
//    public List<SpecificationEnum> getSpecificationDropDownOptions(@RequestParam("classificationValue") String classificationValue) {
//        return specificationDropDownService.getOptions(classificationValue);
//    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute InputDTO inputDTO, Model model) {
        BuildTarget buildTarget = mainService.execute(inputDTO);
        model.addAttribute ("result", buildTarget);
        // Redirect or show success page
        return "redirect:/success";
    }


}

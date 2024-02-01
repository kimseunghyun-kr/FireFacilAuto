package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.util.FormUtilityMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Slf4j
public class Admin {
    @GetMapping("/main")
    public String adminView(){
        return "/admin/main";
    }

    @GetMapping("/lawSelection")
    public String lawSelectionView(Model model) {
        model.addAttribute("buildingForm", new BuildingLawForms());
        model.addAttribute("floorForm", new FloorLawForms());
        model.addAttribute("formUtilityMethod", new FormUtilityMethods());
        return "/admin/lawSelection";
    }

    @PostMapping("/lawSelectionBuild")
    public String BuildlawSelectionFormReceive(BuildingLawForms form, Model model) {
        log.info("BuildingForm , {}", form);
        return"redirect:/admin/main";

    }

    @PostMapping("/lawSelectionFloor")
    public String FloorlawSelectionFormReceive(FloorLawForms form, Model model) {
        log.info("floorForm {}", form);
        return "redirect:/admin/main";
    }
}

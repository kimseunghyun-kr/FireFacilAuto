package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.util.FormUtilityMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("buildingForms", new BuildingLawForms());
        model.addAttribute("floorLawForms", new FloorLawForms());
        model.addAttribute("formUtilityMethod", new FormUtilityMethods());
        return "/admin/lawSelection";
    }
}

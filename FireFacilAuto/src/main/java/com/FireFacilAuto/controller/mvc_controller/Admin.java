package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.service.lawService.LawService;
import com.FireFacilAuto.util.FormUtilityMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class Admin {
    @Autowired
    public LawService lawService;

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
    public String BuildlawSelectionFormReceive(Model model, @RequestBody String request,
                                               @RequestParam("purposesFirst") List<String> purposesFirst,
                                               @RequestParam("purposesSecond") List<String> purposesSecond,
                                               BuildingLawForms form) {
        log.info("request {}", request);
        log.info("BuildingForm , {}", form);
        log.info("request, {} ", purposesFirst);
        log.info("request2 , {}", purposesSecond);
        List<BuildingLawFields> bf = lawService.makeBuildingLaw(form, purposesFirst, purposesSecond);
        return"redirect:/admin/main";

    }



    @PostMapping("/lawSelectionFloor")
    public String FloorlawSelectionFormReceive(Model model, @RequestBody String request,
                                               @RequestParam("purposesFirst") List<String> purposesFirst,
                                               @RequestParam("purposesSecond") List<String> purposesSecond,
                                               FloorLawForms form) {
        log.info("floorForm {}", form);
        List<FloorLawFields> ff = lawService.makeFloorLaw(form, purposesFirst, purposesSecond);
        return "redirect:/admin/main";
    }
}

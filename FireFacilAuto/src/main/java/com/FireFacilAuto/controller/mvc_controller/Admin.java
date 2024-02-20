package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.service.lawService.LawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class Admin {

    @Autowired
    public LawService lawService;

    @GetMapping("/main")
    public String adminView() {
        return "admin/main";
    }

    @GetMapping("/lawSelection")
    public String lawSelectionView(Model model) {
        model.addAttribute("buildingForm", new BuildingLawForms());
        model.addAttribute("floorForm", new FloorLawForms());
        return "admin/lawSelection";
    }

    @PostMapping("/lawSelectionFormReceive")
    public String processLawSelection(Model model,
                                      @RequestParam("formType") String formType,
                                      @RequestParam("purposesFirst") List<String> purposesFirst,
                                      @RequestParam("purposesSecond") List<String> purposesSecond,
                                      @ModelAttribute("buildingForm") BuildingLawForms buildingForm,
                                      @ModelAttribute("floorForm") FloorLawForms floorForm) {

        log.info("Form Type: {}", formType);

        if ("building".equalsIgnoreCase(formType)) {
            log.info("BuildingForm: {}", buildingForm);
            List<BuildingLawFields> buildingFields = lawService.makeBuildingLaw(buildingForm, purposesFirst, purposesSecond);
        } else if ("floor".equalsIgnoreCase(formType)) {
            log.info("FloorForm: {}", floorForm);
            List<FloorLawFields> floorFields = lawService.makeFloorLaw(floorForm, purposesFirst, purposesSecond);
        }

        return "redirect:/admin/main";
    }

    @GetMapping("/paginatedLaws")
    public String getPaginatedLaws(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam String entityType,
                                   Model model) {
        Class<?> entityClass = getEntityClass(entityType);

        Page<?> paginatedLaws = lawService.getPaginatedLaws(page, size, entityClass);

        model.addAttribute("laws", paginatedLaws.getContent());
        model.addAttribute("currentPage", paginatedLaws.getNumber());
        model.addAttribute("totalItems", paginatedLaws.getTotalElements());
        model.addAttribute("totalPages", paginatedLaws.getTotalPages());

        if (BuildingLawFields.class.equals(entityClass)) {
            model.addAttribute("buildingLawFields", BuildingLawForms.allBuildingFields());
            return "admin/buildingLawView";  // Use buildingLaws.html view
        } else if (FloorLawFields.class.equals(entityClass)) {
            model.addAttribute("floorLawFields", FloorLawForms.allFloorFields());
            return "admin/floorLawView";  // Use floorLaws.html view
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }
    }

    private Class<?> getEntityClass(String entityType) {
        return switch (entityType) {
            case "building" -> BuildingLawFields.class;
            case "floor" -> FloorLawFields.class;
            default -> throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        };
    }


}

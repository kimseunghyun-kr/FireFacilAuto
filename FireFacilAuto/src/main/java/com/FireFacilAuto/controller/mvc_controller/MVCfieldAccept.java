package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.DTO.InputDTO;
import com.FireFacilAuto.domain.entity.BuildTarget;
import com.FireFacilAuto.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class MVCfieldAccept {
    private final MainService mainService;

    @Autowired
    public MVCfieldAccept(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/input")
    public String showForm(Model model) {
        model.addAttribute("input", new InputDTO());
        return "inputForm";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute InputDTO inputDTO, Model model) {
        BuildTarget buildTarget = mainService.execute(inputDTO);
        model.addAttribute ("result", buildTarget);
        // Redirect or show success page
        return "redirect:/success";
    }


}

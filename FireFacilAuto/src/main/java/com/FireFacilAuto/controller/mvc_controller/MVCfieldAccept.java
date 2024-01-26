package com.FireFacilAuto.controller.mvc_controller;

import com.FireFacilAuto.domain.configImport.ClassificationList;
import com.FireFacilAuto.domain.DTO.InputDTO;
import com.FireFacilAuto.domain.configImport.Specification;
import com.FireFacilAuto.domain.entity.BuildTarget;
import com.FireFacilAuto.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main")
public class MVCfieldAccept {
    private final ClassificationList classificationList;
    private final MainService mainService;

    @Autowired
    public MVCfieldAccept(ClassificationList classificationList, MainService mainService) {
        this.classificationList = classificationList;
        this.mainService = mainService;
    }

    @GetMapping("/input")
    public String showForm(Model model) {
        log.info("classificationList: {}", classificationList.getClassifications());
        model.addAttribute("input", new InputDTO());
        model.addAttribute("ClassificationConfig", classificationList);
        return "inputform";
    }

    @GetMapping("/getSpecificationOptions")
    @ResponseBody
    public List<Specification> getSpecificationDropDownOptions(@RequestParam("classificationValue") String classificationValue) {
        log.info("valueselected : {}", classificationValue);
        int selectionValue = Integer.parseInt(classificationValue);
        assert(selectionValue > 0);
        return classificationList.getClassifications().get(selectionValue-1).getSpecifications();
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute InputDTO inputDTO, Model model) {
        BuildTarget buildTarget = mainService.execute(inputDTO);
        model.addAttribute ("result", buildTarget);
        // Redirect or show success page
        return "redirect:/result";
    }


}

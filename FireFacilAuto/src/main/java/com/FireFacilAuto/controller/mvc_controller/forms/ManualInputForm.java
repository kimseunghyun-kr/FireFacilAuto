package com.FireFacilAuto.controller.mvc_controller.forms;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.DTO.form.FormFloorDTO;
import com.FireFacilAuto.domain.DTO.form.FormFloorDTOWrapper;
import com.FireFacilAuto.domain.configImport.ClassificationList;
import com.FireFacilAuto.domain.configImport.Specification;
import com.FireFacilAuto.service.formResolver.FormResolverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/main/manualInput")
public class ManualInputForm {
    private final ClassificationList classificationList;
    private final FormResolverService formResolverService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ManualInputForm(ClassificationList classificationList, FormResolverService formResolverService, ObjectMapper objectMapper) {
        this.classificationList = classificationList;
        this.formResolverService = formResolverService;
        this.objectMapper = objectMapper;
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

    @PostMapping("/submitBuilding")
    public String submitFormBuilding(HttpSession httpSession, @ModelAttribute FormBuildingDTO inputDTO, Model model) {
        httpSession.setAttribute("buildTargetInfo", inputDTO);
        FormFloorDTOWrapper dtoWrapper = buildFloorFormDTO(inputDTO);
        httpSession.setAttribute("floorDTOWrapper", dtoWrapper);
        // Redirect or show success page
        return "redirect:/main/manualInput/input/floors";
    }

    @GetMapping("/input/floors")
    public String buildFloors(HttpSession httpSession, Model model) {
        FormFloorDTOWrapper dtoWrapper = (FormFloorDTOWrapper) httpSession.getAttribute("floorDTOWrapper");
        log.info("dto at {} " , dtoWrapper.listWrapper);
        model.addAttribute("floorTargets", dtoWrapper);

        return "main/manualInput/floorInputForm";
    }

    private FormFloorDTOWrapper buildFloorFormDTO(FormBuildingDTO buildTarget) {
        FormFloorDTOWrapper dtoWrapper = new FormFloorDTOWrapper();
        List<FormFloorDTO> floorDTOList = new LinkedList<>();
        int undergroundCounter = 0;
        for (int i = 0; i < buildTarget.floor; i++) {
            FormFloorDTO floorDTO = new FormFloorDTO();
            if (i < buildTarget.undergroundFloors) {

                floorDTO.isUnderground = true;
                undergroundCounter++;
                floorDTO.floorNo = undergroundCounter;

            } else {
                floorDTO.floorNo = i + 1 - undergroundCounter;
                floorDTO.isUnderground = false;
            }
            floorDTO.floorClassification = buildTarget.classification;
            floorDTO.floorSpecification = buildTarget.specification;
            floorDTOList.add(floorDTO);
        }
        dtoWrapper.listWrapper = floorDTOList;
        return dtoWrapper;
    }

    @GetMapping("/getSelectedFloors")
    public ResponseEntity<List<FormFloorDTO>> processSelectedFloors(HttpSession session, @RequestParam("selectedFloors") List<Integer> selectedFloors, Model model) {
        FormFloorDTOWrapper dtoWrapper = (FormFloorDTOWrapper) session.getAttribute("floorDTOWrapper");
        List<FormFloorDTO> result = selectedFloors.stream().map(index -> dtoWrapper.getListWrapper().get(index)).toList();
        log.info("result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/loadSingleFloorContent")
    public ResponseEntity<FormFloorDTO> loadSingleFloorContent(HttpSession session, @RequestParam("selectedFloor") Integer selectedFloor, Model model) {
        FormFloorDTOWrapper dtoWrapper = (FormFloorDTOWrapper) session.getAttribute("floorDTOWrapper");
        // Load content for a single selected floor
        FormFloorDTO result = dtoWrapper.listWrapper.get(selectedFloor);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value="/replicateFloorFields", consumes = "application/json")
    public ResponseEntity<String> replicateFloorFields(HttpSession session, @RequestBody Map<String, Object> requestBody, Model model) {
        FormFloorDTOWrapper dtoWrapper = (FormFloorDTOWrapper) session.getAttribute("floorDTOWrapper");
        List<Integer> selectedFloors = ((List<String>) requestBody.get("selectedFloors")).stream().map(Integer::parseInt).toList();
        log.info("selected floors: {}", selectedFloors);
        FormFloorDTO formFloorDTO = objectMapper.convertValue(requestBody.get("floorForm"), FormFloorDTO.class);
        log.info("formFloorDTO, {}", formFloorDTO);
//        modified DTO

        for (Integer selectedFloor : selectedFloors) {
            FormFloorDTO floorDTOItem = dtoWrapper.getListWrapper().get(selectedFloor);

            if (formFloorDTO.floorWindowAvailability != null) {
                floorDTOItem.setFloorWindowAvailability(formFloorDTO.floorWindowAvailability);
            }
            if (formFloorDTO.floorArea != null) {
                floorDTOItem.setFloorArea(formFloorDTO.floorArea);
            }
            if (formFloorDTO.floorClassification != null) {
                floorDTOItem.setFloorClassification(formFloorDTO.floorClassification);
            }
            if (formFloorDTO.floorSpecification != null) {
                floorDTOItem.setFloorSpecification(formFloorDTO.floorSpecification);
            }
            if (formFloorDTO.floorMaterial != null) {
                floorDTOItem.setFloorMaterial(formFloorDTO.floorMaterial);
            }
        }

        log.info("dto at {} " , dtoWrapper.listWrapper);
        session.setAttribute("floorDTOWrapper", dtoWrapper);
        return new ResponseEntity<>("data sent successfully gucci", HttpStatus.OK);
    }

    @PostMapping("/submitFloors")
    public String submitFormFloors(HttpSession httpSession, @ModelAttribute FormBuildingDTO inputDTO, Model model) {
        httpSession.setAttribute("buildTargetInfo", inputDTO);
        // Redirect or show success page
        return "redirect:/main/manualInput/input/floors";
    }





}




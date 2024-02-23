package com.FireFacilAuto.controller.mvc_controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main")
public class Main {

    @GetMapping("")
    public String mainPage() {
        return "main/index";
    }


}

package com.winners.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/defect-details")
    public String defectDetails() {
        return "defect-details";
    }

}

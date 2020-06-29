package com.hpkarugendo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/blog")
    public String blogs(Model m){
        m.addAttribute("bYes", false);

        return "blogs";
    }
}

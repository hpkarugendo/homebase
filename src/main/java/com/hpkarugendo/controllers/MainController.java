package com.hpkarugendo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }
}

package com.hpkarugendo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error*")
    public String errorHandler(HttpServletRequest req){
        Object error = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(error != null){

            int errorCode = Integer.valueOf(error.toString());

            if(errorCode == 400){
                return "errors/400";
            } else if(errorCode == 403){
                return "errors/403";
            } else if(errorCode == 404) {
                return "errors/404";
            }else if(errorCode == 405){
                return "errors/405";
            } else {
                return "errors/500";
            }

        } else {
            return "errors/500";
        }

    }

    @Override
    public String getErrorPath() {
        return "/error*";
    }
}

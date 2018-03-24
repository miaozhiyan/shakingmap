package com.douyu.supermap.shakingmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("name","mzy");
        return "index";
    }
}

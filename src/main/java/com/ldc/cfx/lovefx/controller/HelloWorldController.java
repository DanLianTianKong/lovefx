package com.ldc.cfx.lovefx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Name:
 * Description:
 * Created by itdragon on 2019-12-06
 */

@Controller
public class HelloWorldController {
    @GetMapping("/hello")
    public String test1(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name",name);

        return "hello";

}

}

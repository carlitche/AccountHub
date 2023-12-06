package com.accounthub.accountweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class BasicController {

    record Post(String author, LocalDate createdOn, String content) {

    }

    @GetMapping("/")
    String rootMapping(Model model){
        return "redirect:/accounts";
    }
}

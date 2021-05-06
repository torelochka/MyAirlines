package ru.itis.zheleznov.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/main", "/index", "/"})
    public String getMainPage() {
        return "main";
    }
}

package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.zheleznov.api.services.CityService;

@Controller
public class MainController {

    private final CityService cityService;

    @Autowired
    public MainController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping({"/main", "/index", "/"})
    public String getMainPage(Model model) {
        model.addAttribute("cities", cityService.allCities());

        return "main";
    }
}

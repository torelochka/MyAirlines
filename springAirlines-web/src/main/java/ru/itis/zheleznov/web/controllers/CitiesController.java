package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.services.CityService;

import java.util.List;

@RestController
public class CitiesController {

    @Autowired
    private final CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/api/cities")
    public List<CityDto> getAllCities() {
        return cityService.allCities();
    }
}

package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.dto.TripForm;
import ru.itis.zheleznov.api.services.CityService;
import ru.itis.zheleznov.api.services.TripService;

import javax.servlet.http.HttpSession;

@Controller
public class TripController {

    private final TripService tripService;
    private final CityService cityService;

    @Autowired
    public TripController(TripService tripService, CityService cityService) {
        this.tripService = tripService;
        this.cityService = cityService;
    }

    @GetMapping("/trips")
    public String getTripsPage(Model model) {
        model.addAttribute("cities", cityService.allCities());

        return "trips";
    }

    @PostMapping("/trip")
    public String findTrip(TripForm tripForm, HttpSession session) {
        CityDto from = CityDto.builder()
                .name(tripForm.getFrom())
                .build();

        CityDto to = CityDto.builder()
                .name(tripForm.getTo())
                .build();

        session.setAttribute("cheapest", tripService.cheapestTrip(from, to));
        session.setAttribute("direct", tripService.directTrip(from, to));
        session.setAttribute("fastest", tripService.fastestTrip(from, to));

        return "redirect:/trips";
    }
}

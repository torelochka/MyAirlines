package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.dto.TripDto;
import ru.itis.zheleznov.api.services.TripService;

@RestController
public class Neo4jTestController {

    private final TripService tripService;

    @Autowired
    public Neo4jTestController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/cheapest")
    public TripDto cheapestTrip() {
        CityDto from = CityDto.builder()
                .name("London")
                .build();

        CityDto to = CityDto.builder()
                .name("Atyrau")
                .build();

        return tripService.cheapestTrip(from, to);
    }

    @GetMapping("/fastest")
    public TripDto fastestTrip() {
        CityDto from = CityDto.builder()
                .name("London")
                .build();

        CityDto to = CityDto.builder()
                .name("Atyrau")
                .build();

        return tripService.fastestTrip(from, to);
    }

    @GetMapping("/direct")
    public TripDto directTrip() {
        CityDto from = CityDto.builder()
                .name("London")
                .build();

        CityDto to = CityDto.builder()
                .name("Atyrau")
                .build();

        return tripService.directTrip(from, to);
    }
}

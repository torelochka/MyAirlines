package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.dto.TripDto;

import java.util.Optional;

public interface TripService {
    Optional<TripDto> cheapestTrip(CityDto from, CityDto to);
    Optional<TripDto> fastestTrip(CityDto from, CityDto to);
    Optional<TripDto> directTrip(CityDto from, CityDto to);
}

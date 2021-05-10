package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.dto.TripDto;

public interface TripService {
    TripDto cheapestTrip(CityDto from, CityDto to);
    TripDto fastestTrip(CityDto from, CityDto to);
    TripDto directTrip(CityDto from, CityDto to);
}

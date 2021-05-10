package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.CityDto;

import java.util.List;

public interface CityService {

    List<CityDto> allCities();
}

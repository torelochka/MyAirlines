package ru.itis.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.zheleznov.api.dto.BookingDto;
import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.dto.TripDto;
import ru.itis.zheleznov.api.dto.UserDto;
import ru.itis.zheleznov.api.services.BookingService;
import ru.itis.zheleznov.impl.models.Booking;
import ru.itis.zheleznov.impl.models.City;
import ru.itis.zheleznov.impl.models.Trip;
import ru.itis.zheleznov.impl.models.User;
import ru.itis.zheleznov.impl.repositories.BookingRepository;
import ru.itis.zheleznov.impl.repositories.CityRepository;
import ru.itis.zheleznov.impl.repositories.TripRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CityRepository cityRepository;
    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, CityRepository cityRepository, TripRepository tripRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.cityRepository = cityRepository;
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void booking(BookingDto bookingDto) {
        List<City> cities = new ArrayList<>();
        for (CityDto cityDto : bookingDto.getTrip().getCities()) {
            City city = cityRepository.findById(cityDto.getId())
                    .orElseThrow(IllegalArgumentException::new);

            cities.add(city);
        }

        Trip trip = Trip.builder()
                .cities(cities)
                .cost(bookingDto.getTrip().getCost())
                .time(bookingDto.getTrip().getTime())
                .build();

        tripRepository.save(trip);

        Booking booking = Booking.builder()
                .trip(trip)
                .user(modelMapper.map(bookingDto.getUser(), User.class))
                .build();

        bookingRepository.save(booking);
    }

    @Override
    public List<BookingDto> userBookings(UserDto user) {
        return bookingRepository.findAllByUser(modelMapper.map(user, User.class))
                .stream().map(booking -> modelMapper.map(booking, BookingDto.class))
                .collect(Collectors.toList());
    }
}

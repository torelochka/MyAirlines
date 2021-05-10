package ru.itis.zheleznov.api.services;

import ru.itis.zheleznov.api.dto.BookingDto;
import ru.itis.zheleznov.api.dto.UserDto;

import java.util.List;

public interface BookingService {

    void booking(BookingDto bookingDto);

    List<BookingDto> userBookings(UserDto user);
}

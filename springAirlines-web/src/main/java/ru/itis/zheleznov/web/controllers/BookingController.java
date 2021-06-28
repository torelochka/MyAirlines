package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.zheleznov.api.dto.BookingDto;
import ru.itis.zheleznov.api.dto.TripDto;
import ru.itis.zheleznov.api.dto.UserDto;
import ru.itis.zheleznov.api.services.BookingService;
import ru.itis.zheleznov.api.services.UserService;
import ru.itis.zheleznov.impl.models.Booking;
import ru.itis.zheleznov.web.security.details.UserDetailsImpl;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/booking/{tripType}")
    public String bookingTrip(@PathVariable String tripType, HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");

        UserDto userDto = userService.userById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Optional<TripDto> trip = (Optional<TripDto>) session.getAttribute(tripType);
        if (trip.isPresent()) {
            BookingDto bookingDto = BookingDto.builder()
                    .trip(trip.get())
                    .user(userDto)
                    .build();

            bookingService.booking(bookingDto);
        }

        return "redirect:/profile";
    }
}

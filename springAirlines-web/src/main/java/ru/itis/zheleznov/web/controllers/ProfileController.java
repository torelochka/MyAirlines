package ru.itis.zheleznov.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.zheleznov.api.dto.BookingDto;
import ru.itis.zheleznov.api.dto.UserDto;
import ru.itis.zheleznov.api.services.BookingService;
import ru.itis.zheleznov.api.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {

    private final BookingService bookingService;

    @Autowired
    public ProfileController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");

        List<BookingDto> userBookings = bookingService.userBookings(user);

        model.addAttribute("bookings", userBookings);

        return "profile";
    }
}

package ru.itis.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.zheleznov.api.dto.BookingDto;
import ru.itis.zheleznov.impl.models.Booking;
import ru.itis.zheleznov.impl.models.User;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
}

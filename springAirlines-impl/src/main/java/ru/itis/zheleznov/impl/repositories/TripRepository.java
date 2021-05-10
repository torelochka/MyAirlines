package ru.itis.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.zheleznov.impl.models.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}

package com.CarPool.repository;

import com.CarPool.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRideDate(LocalDate date);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByProviderId(Long providerId);
}

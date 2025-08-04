package com.CarPool.repository;

import com.CarPool.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRideDate(LocalDate date);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByProviderId(Long providerId);
    List<Booking> findByProviderIdAndRideDateAndRideType(Long providerId, LocalDate rideDate, String rideType);

    @Query("SELECT b FROM Booking b WHERE b.user IS NULL AND b.status = 'available' AND b.provider.college.id = :collegeId")
    List<Booking> findAvailableBookingsByCollegeId(@Param("collegeId") Long collegeId);

}

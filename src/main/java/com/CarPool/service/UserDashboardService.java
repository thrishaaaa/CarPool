package com.CarPool.service;

import com.CarPool.model.Provider;
import com.CarPool.model.User;
import com.CarPool.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface UserDashboardService {
    List<Provider> getAvailableProviders(Long collegeId);
    List<Provider> searchProviders(String area, String carModel, Long collegeId);
    String bookRide(User user, Long providerId, String rideType, LocalDate rideDate);
    String cancelBooking(Long bookingId);
    List<Booking> getBookingHistory(Long userId);
    List<String> getNotifications(Long userId);
    Double calculateFare(Long providerId);

    Double estimateFare(Long providerId);

    User getUserById(Long id);

    List<Booking> getAvailableBookings(Long collegeId);


}

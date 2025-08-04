package com.CarPool.service;

import com.CarPool.model.Booking;
import java.util.List;

public interface BookingService {
    List<Booking> getUserBookings(Long userId);
    void cancelBooking(Long bookingId);
    // Add any other methods you use in UserController
}

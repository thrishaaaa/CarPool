package com.CarPool.service.impl;

import com.CarPool.model.Booking;
import com.CarPool.model.Provider;
import com.CarPool.repository.BookingRepository;
import com.CarPool.repository.ProviderRepository;
import com.CarPool.service.ProviderDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProviderDashboardServiceImpl implements ProviderDashboardService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public List<Booking> getBookingsForProvider(Long providerId) {
        return bookingRepository.findByProviderId(providerId);
    }

    @Override
    public String addRide(Provider provider, String rideType, LocalDate rideDate) {
        Booking booking = new Booking();
        booking.setProvider(provider);
        booking.setUser(null);
        booking.setRideType(rideType);
        booking.setRideDate(rideDate);
        booking.setStatus("available");

        bookingRepository.save(booking);

        return "Ride added successfully for " + rideType + " on " + rideDate;
    }


    @Override
    public String cancelRide(Long providerId, String rideType, LocalDate rideDate) {
        List<Booking> bookings = bookingRepository.findByProviderIdAndRideDateAndRideType(providerId, rideDate, rideType);

        for (Booking booking : bookings) {
            booking.setStatus("cancelled");
        }

        bookingRepository.saveAll(bookings);
        return "All bookings for " + rideType + " on " + rideDate + " have been cancelled.";
    }
}

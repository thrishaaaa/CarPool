// UserDashboardServiceImpl.java
package com.CarPool.service.impl;

import com.CarPool.model.Booking;
import com.CarPool.model.Provider;
import com.CarPool.model.Settings;
import com.CarPool.model.User;
import com.CarPool.repository.BookingRepository;
import com.CarPool.repository.ProviderRepository;
import com.CarPool.repository.SettingsRepository;
import com.CarPool.repository.UserRepository;
import com.CarPool.service.UserDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Provider> getAvailableProviders(Long collegeId) {
        return providerRepository.findByCollegeId(collegeId);
    }

    @Override
    public List<Provider> searchProviders(String area, String carModel, Long collegeId) {
        return providerRepository.findByCollegeIdAndAreaContainingIgnoreCaseAndCarModelContainingIgnoreCase(
                collegeId, area, carModel
        );
    }

    @Override
    public String bookRide(User user, Long providerId, String rideType, LocalDate rideDate) {
        Settings settings = settingsRepository.findById(1L).orElse(null);

        if (settings != null) {
            try {
                int deadlineHour = Integer.parseInt(settings.getBookingDeadline());
                LocalDateTime bookingDeadline = LocalDateTime.of(rideDate.minusDays(1), LocalTime.of(deadlineHour, 0));

                if (LocalDateTime.now().isAfter(bookingDeadline)) {
                    return "Booking deadline has passed.";
                }
            } catch (NumberFormatException e) {
                return "Invalid booking deadline configuration.";
            }
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProvider(providerRepository.findById(providerId).orElse(null));
        booking.setRideDate(rideDate);
        booking.setRideType(rideType);
        booking.setStatus("booked");

        bookingRepository.save(booking);
        return "Ride booked successfully.";
    }


    @Override
    public String cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return "Invalid booking.";

        Settings settings = settingsRepository.findById(1L).orElse(null);

        if (settings != null) {
            try {
                int deadlineHour = Integer.parseInt(settings.getBookingDeadline());
                LocalDateTime cancelDeadline = LocalDateTime.of(booking.getRideDate().minusDays(1), LocalTime.of(deadlineHour, 0));

                if (LocalDateTime.now().isAfter(cancelDeadline)) {
                    return "Cannot cancel, deadline has passed.";
                }
            } catch (NumberFormatException e) {
                return "Invalid booking deadline configuration.";
            }
        }

        booking.setStatus("cancelled");
        bookingRepository.save(booking);
        return "Booking cancelled.";
    }

    @Override
    public List<Booking> getBookingHistory(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<String> getNotifications(Long userId) {
        List<String> notifications = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        for (Booking booking : bookings) {
            if ("cancelled".equals(booking.getStatus())) {
                notifications.add("Your booking on " + booking.getRideDate() + " with provider " + booking.getProvider().getName() + " was cancelled.");
            }
        }
        return notifications;
    }

    @Override
    public Double calculateFare(Long providerId) {
        Settings settings = settingsRepository.findById(1L).orElse(null);
        if (settings == null) return 0.0;
        double costPerKm = settings.getFuelCostPerKm();
        return costPerKm * 10; // Assuming 10km default ride length
    }
    @Override
    public Double estimateFare(Long providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Provider ID. Please enter a valid ID."));

        double fuelCost = settingsRepository.findById(1L)
                .map(Settings::getFuelCostPerKm)
                .orElse(0.0);

        // Assume fixed average distance per ride = 10km (replace with actual logic if needed)
        double estimatedDistance = 10.0;

        return fuelCost * estimatedDistance;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getAvailableBookings(Long collegeId) {
        return bookingRepository.findAvailableBookingsByCollegeId(collegeId);
    }





}

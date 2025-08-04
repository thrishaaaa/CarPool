package com.CarPool.service;

import com.CarPool.model.Booking;
import com.CarPool.model.Provider;

import java.time.LocalDate;
import java.util.List;

public interface ProviderDashboardService {

    List<Booking> getBookingsForProvider(Long providerId);

    String addRide(Provider provider, String rideType, LocalDate rideDate);

    String cancelRide(Long providerId, String rideType, LocalDate rideDate);
}

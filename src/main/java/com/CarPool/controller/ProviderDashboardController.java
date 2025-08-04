package com.CarPool.controller;

import com.CarPool.model.Booking;
import com.CarPool.model.Provider;
import com.CarPool.service.ProviderDashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/provider/dashboard")
public class ProviderDashboardController {

    @Autowired
    private ProviderDashboardService providerDashboardService;

    @GetMapping
    public String showDashboard(HttpSession session, Model model) {
        Provider provider = (Provider) session.getAttribute("loggedInProvider");
        if (provider == null) return "redirect:/provider/login";

        model.addAttribute("provider", provider);
        return "provider/dashboard";
    }

    @GetMapping("/view-bookings")
    public String viewBookings(HttpSession session, Model model) {
        Provider provider = (Provider) session.getAttribute("loggedInProvider");
        if (provider == null) return "redirect:/provider/login";

        List<Booking> bookings = providerDashboardService.getBookingsForProvider(provider.getId());
        model.addAttribute("bookings", bookings);
        return "provider/view_bookings";
    }

    @GetMapping("/add-ride")
    public String showAddRideForm() {
        return "provider/add_ride";
    }

    @PostMapping("/add-ride")
    public String addRide(@RequestParam String rideType,
                          @RequestParam String rideDate,
                          HttpSession session, Model model) {
        Provider provider = (Provider) session.getAttribute("loggedInProvider");
        if (provider == null) return "redirect:/provider/login";

        String result = providerDashboardService.addRide(provider, rideType, LocalDate.parse(rideDate));
        model.addAttribute("message", result);
        return "redirect:/provider/dashboard";
    }

    @GetMapping("/cancel-ride")
    public String showCancelRideForm() {
        return "provider/cancel_ride";
    }

    @PostMapping("/cancel-ride")
    public String cancelRide(@RequestParam String rideType,
                             @RequestParam String rideDate,
                             HttpSession session, Model model) {
        Provider provider = (Provider) session.getAttribute("loggedInProvider");
        if (provider == null) return "redirect:/provider/login";

        String result = providerDashboardService.cancelRide(provider.getId(), rideType, LocalDate.parse(rideDate));
        model.addAttribute("message", result);
        return "redirect:/provider/dashboard";
    }
}

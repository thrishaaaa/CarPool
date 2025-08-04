package com.CarPool.controller;

import com.CarPool.model.Provider;
import com.CarPool.model.User;
import com.CarPool.model.Booking;
import com.CarPool.service.UserDashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user/dashboard")
public class UserDashboardController {

    @Autowired
    private UserDashboardService userDashboardService;

    @GetMapping
    public String showDashboard(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        User loggedInUser = userDashboardService.getUserById(userId);
        List<Booking> availableRides = userDashboardService.getAvailableBookings(loggedInUser.getCollege().getId());

        model.addAttribute("user", loggedInUser);
        model.addAttribute("availableRides", availableRides);

        return "user/dashboard";
    }


    @GetMapping("/search")
    public String searchProviders(@RequestParam(required = false) String area,
                                  @RequestParam(required = false) String carModel,
                                  Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        User user = userDashboardService.getUserById(userId);
        List<Provider> providers = userDashboardService.searchProviders(area, carModel, user.getCollege().getId());
        model.addAttribute("providers", providers);
        model.addAttribute("user", user);

        return "user/dashboard";
    }

    @PostMapping("/book/{providerId}")
    public String bookRide(@PathVariable Long providerId,
                           @RequestParam String rideType,
                           @RequestParam String rideDate,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        User user = userDashboardService.getUserById(userId);
        String result = userDashboardService.bookRide(user, providerId, rideType, LocalDate.parse(rideDate));

        redirectAttributes.addFlashAttribute("message", result);

        return "redirect:/user/dashboard";
    }


    @PostMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        String result = userDashboardService.cancelBooking(bookingId);
        model.addAttribute("message", result);
        return "redirect:/user/dashboard/history";
    }


    @GetMapping("/history")
    public String viewBookingHistory(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        User user = userDashboardService.getUserById(userId);
        List<Booking> history = userDashboardService.getBookingHistory(user.getId());
        model.addAttribute("history", history);

        return "user/booking_history";
    }

    @GetMapping("/notifications")
    public String viewNotifications(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/user/login";

        User user = userDashboardService.getUserById(userId);
        List<String> notifications = userDashboardService.getNotifications(user.getId());
        model.addAttribute("notifications", notifications);

        return "user/notifications";
    }

    @GetMapping("/fare-estimate")
    public String estimateFare(@RequestParam(required = false) Long providerId,
                               Model model) {
        if (providerId != null) {
            try {
                Double fare = userDashboardService.estimateFare(providerId);
                model.addAttribute("fare", fare);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return "user/fare_estimate";
    }


}

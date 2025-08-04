package com.CarPool.controller;
import com.CarPool.model.User;
import com.CarPool.repository.CollegeRepository;
import com.CarPool.service.BookingService;
import com.CarPool.service.ProviderService;
import com.CarPool.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/register")
    public String showUserRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("colleges", collegeRepository.findAll());
        return "user/register";
    }

    @PostMapping("/register")
    public String processUserRegistration(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("user", user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showUserLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String processUserLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User existingUser = userService.findByNameAndPassword(user.getName(), user.getPassword());

        if (existingUser != null) {
            session.setAttribute("userId", existingUser.getId());
            return "redirect:/user/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "user/login";
        }
    }





}



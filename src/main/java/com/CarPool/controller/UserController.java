package com.CarPool.controller;

import com.CarPool.model.User;
import com.CarPool.model.College;
import com.CarPool.repository.CollegeRepository;
import com.CarPool.service.UserService;
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
    private CollegeRepository collegeRepository;

    // --- Registration ---

    @GetMapping("/register")
    public String showUserRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("colleges", collegeRepository.findAll());
        return "user/register"; // templates/user/register.html
    }

    @PostMapping("/register")
    public String processUserRegistration(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("user", user);
        return "redirect:/user/login";
    }

    // --- Login ---

    @GetMapping("/login")
    public String showUserLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login"; // templates/user/login.html
    }

    @PostMapping("/login")
    public String processUserLogin(@ModelAttribute("user") User user, Model model) {
        User existingUser = userService.findById(user.getId());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("user", existingUser);
            return "user/dashboard"; // create templates/user/dashboard.html
        } else {
            model.addAttribute("error", "Invalid ID or password");
            return "user/login";
        }
    }
}

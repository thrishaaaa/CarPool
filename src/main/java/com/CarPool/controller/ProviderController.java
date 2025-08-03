package com.CarPool.controller;

import com.CarPool.model.Provider;
import com.CarPool.repository.CollegeRepository;
import com.CarPool.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CollegeRepository collegeRepository;

    @GetMapping("/register")
    public String showProviderRegistrationForm(Model model) {
        model.addAttribute("provider", new Provider());
        model.addAttribute("colleges", collegeRepository.findAll()); // 🔥 this line adds the college list
        return "provider/register";
    }


    @PostMapping("/register")
    public String processProviderRegistration(@ModelAttribute("provider") Provider provider, Model model) {
        providerService.saveProvider(provider);
        model.addAttribute("provider", provider);
        return "redirect:/provider/login"; // You can make a success page or redirect to login
    }

    @GetMapping("/login")
    public String showProviderLoginForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "provider/login";
    }

    @PostMapping("/login")
    public String processProviderLogin(@ModelAttribute("provider") Provider provider, Model model) {
        Provider existingProvider = providerService.findByEmail(provider.getEmail());

        if (existingProvider != null && existingProvider.getPassword().equals(provider.getPassword())) {
            // Login successful
            model.addAttribute("provider", existingProvider);
            return "provider/dashboard"; // create dashboard.html later
        } else {
            // Login failed
            model.addAttribute("error", "Invalid email or password");
            return "provider/login";
        }
    }


}

package com.CarPool.controller;

import com.CarPool.model.Provider;
import com.CarPool.repository.CollegeRepository;
import com.CarPool.service.ProviderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("colleges", collegeRepository.findAll());
        return "provider/register";
    }

    @PostMapping("/register")
    public String processProviderRegistration(@ModelAttribute("provider") Provider provider, Model model) {
        providerService.saveProvider(provider);
        return "redirect:/provider/login";
    }


    @GetMapping("/login")
    public String showProviderLoginForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "provider/login"; // templates/provider/login.html
    }

    @PostMapping("/login")
    public String processProviderLogin(@ModelAttribute("provider") Provider provider,
                                       Model model,
                                       HttpSession session) {
        Provider existingProvider = providerService.findByEmail(provider.getEmail());

        if (existingProvider != null && existingProvider.getPassword().equals(provider.getPassword())) {
            session.setAttribute("loggedInProvider", existingProvider);
            return "redirect:/provider/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "provider/login";
        }
    }

    @GetMapping("/logout")
    public String logoutProvider(HttpSession session) {
        session.invalidate();
        return "redirect:/provider/login";
    }
}

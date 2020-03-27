package com.example.demo.controller;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.java.Log;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log
@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String signUp(@ModelAttribute UserRegistrationDto userDto, Model model) {
        System.out.println("test");
        if (userService.isUserPresent(userDto.getEmail())) {
            model.addAttribute("exist", true);
            return "registration";
        }
        userService.save(userDto);
        return "redirect:/login";
    }
}


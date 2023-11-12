package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {

        if (!registrationService.userDataIsCorrect(user.getUsername(), user.getPassword()))
            return "registration";

        if (registrationService.isUserExist(user))
            return "registration";

        registrationService.saveUser(user);

        return "redirect:/login";
    }
}
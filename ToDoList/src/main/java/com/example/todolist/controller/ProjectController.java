package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    @ResponseBody
    @GetMapping("/project/{id}")
    public String addProject(@AuthenticationPrincipal User user,
                             @PathVariable Long id) {

        return "project number " + id;
    }
}

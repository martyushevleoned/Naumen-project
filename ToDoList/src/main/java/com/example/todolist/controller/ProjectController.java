package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.ProjectService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("")
    public String root(@AuthenticationPrincipal User user, Model model) {
        return home(user, model);
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("projects", projectService.getAllProjects(user));
        return "home";
    }

    @ResponseBody
    @GetMapping("/home/add/project")
    public ResponseEntity<HttpStatus> projectList(@AuthenticationPrincipal User user,
                                                  @RequestParam String projectName) {
        projectService.addProject(user, projectName);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

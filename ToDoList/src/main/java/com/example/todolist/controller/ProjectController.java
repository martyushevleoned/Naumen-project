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

    @GetMapping("/projects")
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("projects", projectService.getAllProjects(user));
        return "projects";
    }

    @ResponseBody
    @GetMapping("/projects/add")
    public Long addProject(@AuthenticationPrincipal User user,
                                                  @RequestParam String projectName) {

        return projectService.addProject(user, projectName);
    }

    @ResponseBody
    @GetMapping("/projects/delete")
    public void deleteProject(@AuthenticationPrincipal User user,
                            @RequestParam Long id) {

        projectService.deleteProject(user, id);
    }
}

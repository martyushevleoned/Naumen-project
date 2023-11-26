package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.DtoService;
import com.example.todolist.service.ProjectService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectsListController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DtoService dtoService;

    @GetMapping("/projects")
    public String projects(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("projects", dtoService.getMyProjects(user));
        return "projectList";
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

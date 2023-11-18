package com.example.todolist.controller;

import com.example.todolist.model.dto.projectPage.ProjectDto;
import com.example.todolist.model.entity.User;
import com.example.todolist.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @ResponseBody
    @GetMapping("/project/{id}")
    public ProjectDto addProject(@AuthenticationPrincipal User user,
                                 @PathVariable Long id) {

        return projectService.getProjectInfo(user, id);
    }
}

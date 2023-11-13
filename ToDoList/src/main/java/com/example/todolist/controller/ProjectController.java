package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @ResponseBody
    @GetMapping("/add/project")
    public ResponseEntity<HttpStatus> projectList(@AuthenticationPrincipal User user,
                                                  @RequestParam String projectName) {
        projectService.addProject(user, projectName);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

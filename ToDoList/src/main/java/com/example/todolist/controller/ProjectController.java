package com.example.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    @ResponseBody
    public String projectList(){
        return "Some";
    }
}

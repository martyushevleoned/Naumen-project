package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.DtoService;
import com.example.todolist.service.entityService.MemberService;
import com.example.todolist.service.entityService.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProjectListController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DtoService dtoService;

    @GetMapping("/projects")
    public String projects(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("projects", dtoService.getMyProjects(user));
        model.addAttribute("foreignProjects", dtoService.getForeignProjects(user));
        return "projectList";
    }

    @ResponseBody
    @GetMapping("/projects/add")
    public ResponseEntity<Long> addProject(@AuthenticationPrincipal User user,
                                           @RequestParam String projectName) {

        Long projectId = projectService.addProject(user, projectName);

        if (projectId == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(projectId);
    }

    @GetMapping("/projects/delete")
    public ResponseEntity<String> deleteProject(@AuthenticationPrincipal User user,
                                                @RequestParam Long projectId) {

        if (projectService.deleteProject(user, projectId))
            return ResponseEntity.status(HttpStatus.OK).body("OK (CODE 200)\n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR (CODE 500)\n");
    }

    @ResponseBody
    @GetMapping("/projects/delete/member")
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal User user,
                             @RequestParam Long projectId) {

        if (memberService.deleteMember(user, projectId))
            return ResponseEntity.status(HttpStatus.OK).body("OK (CODE 200)\n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR (CODE 500)\n");
    }
}

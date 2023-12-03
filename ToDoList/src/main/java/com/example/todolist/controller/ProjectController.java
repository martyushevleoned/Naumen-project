package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.*;
import com.example.todolist.service.entityService.MemberService;
import com.example.todolist.service.entityService.MessageService;
import com.example.todolist.service.entityService.ProjectService;
import com.example.todolist.service.entityService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DtoService dtoService;


    @GetMapping("/project/{id}")
    public String getProject(@AuthenticationPrincipal User user,
                             @PathVariable Long id,
                             Model model) {

        if (!projectService.haveAccess(user.getUsername(), id))
            return "projectList";

        model.addAttribute("project", dtoService.getProject(user, id));
        return "project";
    }

    @ResponseBody
    @GetMapping("/project/add/task")
    public Long addTask(@AuthenticationPrincipal User user,
                        @RequestParam Long projectId,
                        @RequestParam String text) {

        return taskService.addTask(user, projectId, text);
    }

    @ResponseBody
    @GetMapping("/project/delete/task")
    public void deleteTask(@AuthenticationPrincipal User user,
                           @RequestParam Long taskId) {

        taskService.removeTask(user, taskId);
    }

    @ResponseBody
    @GetMapping("/project/add/message")
    public Long addMessage(@AuthenticationPrincipal User user,
                           @RequestParam Long projectId,
                           @RequestParam String text) {

        return messageService.addMessage(user, projectId, text);
    }

    @ResponseBody
    @GetMapping("/project/add/member")
    public Long addMember(@AuthenticationPrincipal User user,
                             @RequestParam Long projectId,
                             @RequestParam String username) {

        return memberService.addMember(user, projectId, username);
    }
}

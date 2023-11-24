package com.example.todolist.controller;

import com.example.todolist.model.dto.projectPage.ProjectDto;
import com.example.todolist.model.entity.User;
import com.example.todolist.service.MemberService;
import com.example.todolist.service.MessageService;
import com.example.todolist.service.ProjectService;
import com.example.todolist.service.TaskService;
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

    @GetMapping("/project/{id}")
    public String addProject(@AuthenticationPrincipal User user,
                             @PathVariable Long id,
                             Model model) {

        model.addAttribute("project", projectService.getProjectInfo(user, id));
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
                           @RequestParam Long id) {

        taskService.removeTask(user, id);
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
    public Boolean addMember(@AuthenticationPrincipal User user,
                             @RequestParam Long projectId,
                             @RequestParam String username) {

        return memberService.addMember(user, projectId, username);
    }
}

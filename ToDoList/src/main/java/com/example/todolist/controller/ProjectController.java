package com.example.todolist.controller;

import com.example.todolist.model.entity.User;
import com.example.todolist.service.*;
import com.example.todolist.service.entityService.MemberService;
import com.example.todolist.service.entityService.MessageService;
import com.example.todolist.service.entityService.ProjectService;
import com.example.todolist.service.entityService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ValidationService validationService;


    @GetMapping("/project/{id}")
    public String getProject(@AuthenticationPrincipal User user,
                             @PathVariable Long id,
                             Model model) {

        if (!validationService.haveAccess(user.getUsername(), id))
            return "projectList";

        model.addAttribute("project", dtoService.getProject(user, id));
        return "project";
    }

    @ResponseBody
    @GetMapping("/project/add/task")
    public ResponseEntity<Long> addTask(@AuthenticationPrincipal User user,
                        @RequestParam Long projectId,
                        @RequestParam String text) {

        Long taskId = taskService.addTask(user, projectId, text);

        if (taskId == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(taskId);
    }

    @ResponseBody
    @GetMapping("/project/delete/task")
    public ResponseEntity<String> deleteTask(@AuthenticationPrincipal User user,
                           @RequestParam Long taskId) {

        if (taskService.removeTask(user, taskId))
            return ResponseEntity.status(HttpStatus.OK).body("OK (CODE 200)\n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR (CODE 500)\n");
    }

    @ResponseBody
    @GetMapping("/project/add/message")
    public ResponseEntity<Long> addMessage(@AuthenticationPrincipal User user,
                           @RequestParam Long projectId,
                           @RequestParam String text) {

        Long messageId = messageService.addMessage(user, projectId, text);

        if (messageId == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(messageId);
    }

    @ResponseBody
    @GetMapping("/project/add/member")
    public ResponseEntity<Long> addMember(@AuthenticationPrincipal User user,
                                            @RequestParam Long projectId,
                                            @RequestParam String username) {

        Long userId = memberService.addMember(user, projectId, username);

        if (userId == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}

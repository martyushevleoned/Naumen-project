package com.example.todolist.service;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.TaskRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    public Long addTask(User user, Long projectId, String text){

        Optional<Project> projectDB = projectRepository.findById(projectId);
        if (projectDB.isEmpty())
            return null;

        Project project = projectDB.get();

        if (!Objects.equals(project.getUser().getUsername(), user.getUsername()))
            return null;

        Date date = new Date();

        Task task = new Task();
        task.setText(text);
        task.setProject(project);
        task.setCreationDateTime(date);
        taskRepository.save(task);

        return taskRepository.findByProjectAndTextAndDatetime(project,text,date).get(0).getId();
    }
}

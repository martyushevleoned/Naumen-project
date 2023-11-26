package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskRepository taskRepository;

    public Long addTask(User user, Long projectId, String text){

        Optional<Project> projectDB = projectService.projectAccess(user, projectId);
        if (projectDB.isEmpty())
            return null;

        Project project = projectDB.get();

        if (!Objects.equals(project.getUser().getUsername(), user.getUsername()))
            return null;

        Date date = new Date();
        Task task = new Task(text,date, project);
        taskRepository.save(task);

        return taskRepository.findByProjectAndTextAndDatetime(project,text,date).get(0).getId();
    }

    public void removeTask(User user, Long id){

        taskRepository.findById(id).ifPresent(t -> {
            if (Objects.equals(user.getUsername(), t.getProject().getUser().getUsername()))
                taskRepository.delete(t);
        });
    }
}

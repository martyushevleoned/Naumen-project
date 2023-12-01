package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskRepository taskRepository;

    public Long addTask(User user, Long projectId, String text){

//        Проверка на доступ к проекту и существование сущностей
        if (!projectService.haveAccess(user.getUsername(), projectId))
            return null;

        Project project = projectRepository.getReferenceById(projectId);

//        Проверка на то что пользователь является владельцем проекта
        if (!project.getUser().equals(user))
            return null;

//        Создание и сохранение сущности
        Date date = new Date();
        Task task = new Task(text,date, project);
        taskRepository.save(task);

//        Достаём id созданной задачи из бд
        return taskRepository.findByProjectAndTextAndDatetime(project,text,date).get(0).getId();
    }

    public void removeTask(User user, Long id){

//        Проверяем существование задачи
        taskRepository.findById(id).ifPresent(t -> {

//            Удаляем задачу если пользователь является его владельцем
            if (t.getProject().getUser().equals(user))
                taskRepository.delete(t);
        });
    }
}

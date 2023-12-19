package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.TaskRepository;
import com.example.todolist.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ValidationService validationService;

    public Long addTask(User user, Long projectId, String text) {

//        Проверка на доступ к проекту и существование сущностей
        if (!validationService.haveAccess(user.getUsername(), projectId))
            return null;

        Project project = projectRepository.getReferenceById(projectId);

//        Проверка на то что пользователь является владельцем проекта
        if (!project.getUser().equals(user))
            return null;

//        Создание и сохранение сущности
        Date date = new Date();
        Task task = new Task(text, date, project);
        taskRepository.save(task);

//        Достаём id созданной задачи из бд
        return taskRepository.findIdByProjectAndTextAndDatetime(project, text, date);
    }

    public boolean removeTask(User user, Long id) {

        Optional<Task> task = taskRepository.findById(id);

//        Проверяем существование задачи
        if (task.isPresent())
//            Удаляем задачу если пользователь является его владельцем
            if (task.get().getProject().getUser().equals(user)){
                taskRepository.deleteById(id);
                return true;
            }

        return false;
    }
}

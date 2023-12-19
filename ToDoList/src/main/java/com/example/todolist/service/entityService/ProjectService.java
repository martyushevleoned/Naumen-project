package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Long addProject(User user, String projectName) {

//        Создаём и сохраняем проект
        Date date = new Date();
        Project project = new Project(projectName, date, user);
        projectRepository.save(project);

//        Достаём id проекта только что созданной записи
        return projectRepository.findIdByUserAndNameAndDatetime(user, projectName, date);
    }

    public boolean deleteProject(User user, Long id) {

        Optional<Project> project = projectRepository.findById(id);

//        Проверка на существование удаляемого проекта
        if (project.isPresent())
//            Проект будет удалён только если пользователь является его владельцем
            if (project.get().getUser().equals(user)){
                projectRepository.delete(project.get());
                return true;
            }

        return false;
    }
}

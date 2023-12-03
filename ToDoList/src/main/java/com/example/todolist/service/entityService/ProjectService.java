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

//    Данная функция проверяет является ли пользователь участником проекта
    public boolean haveAccess(String username, Long projectId) {

//        Проверка на существование пользователя и проекта в базе данных
        Optional<User> userDB = Optional.ofNullable(userRepository.findByUsername(username));
        Optional<Project> projectDB = projectRepository.findById(projectId);
        if (userDB.isEmpty() || projectDB.isEmpty())
            return false;

//        пользователь имеет доступ если он владелец проекта
        if (userDB.get().equals(projectDB.get().getUser()))
            return true;

//        пользователь имеет доступ если есть соответствующая запись в таблице members
        for (Member m : projectDB.get().getMembers())
            if (userDB.get().equals(m.getUser()))
                return true;

//        в остальных случаях пользователь не имеет доступа к проекту
        return false;
    }
}

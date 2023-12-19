package com.example.todolist.service;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

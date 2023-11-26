package com.example.todolist.service;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccessService {

    @Autowired
    private ProjectRepository projectRepository;

    public boolean haveAccess(User user, Long projectId) {

        Optional<Project> projectDB = projectRepository.findById(projectId);

//        Проект не существует
        if (projectDB.isEmpty())
            return false;

//        Обращается владелец проекта
        if (Objects.equals(projectDB.get().getUser().getUsername(), user.getUsername()))
            return true;

//        Обращается пользователь имеющий доступ
        for(Member m: projectDB.get().getMembers())
            if (Objects.equals(m.getUser().getUsername(), user.getUsername()))
                return true;

        return false;
    }
}

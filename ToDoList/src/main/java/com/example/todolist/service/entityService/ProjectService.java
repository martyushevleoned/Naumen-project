package com.example.todolist.service.entityService;

import com.example.todolist.model.dto.projectPage.MemberDto;
import com.example.todolist.model.dto.projectPage.MessageDto;
import com.example.todolist.model.dto.projectPage.ProjectDto;
import com.example.todolist.model.dto.projectPage.TaskDto;
import com.example.todolist.model.dto.projectsListPage.ProjectCardDto;
import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Long addProject(User user, String projectName) {

        Date date = new Date();

        Project project = new Project(projectName, date, user);
        projectRepository.save(project);

        return projectRepository.findByUserAndNameAndDatetime(user, projectName, date).get(0).getId();
    }

    public void deleteProject(User user, Long id) {

        projectRepository.findById(id).ifPresent(p -> {
            if (Objects.equals(p.getUser().getUsername(), user.getUsername()))
                projectRepository.delete(p);
        });
    }

    public Optional<Project> projectAccess(User user, Long projectId) {

        Optional<Project> projectDB = projectRepository.findById(projectId);

//        Проект не существует
        if (projectDB.isEmpty())
            return Optional.empty();

//        Обращается владелец проекта
        if (projectDB.get().getUser().equals(user))
            return projectDB;

//        Обращается пользователь имеющий доступ
        for(Member m: projectDB.get().getMembers())
            if (m.getUser().equals(user))
                return projectDB;

//        Пользователь не имеет доступа к проекту
        return Optional.empty();
    }
}

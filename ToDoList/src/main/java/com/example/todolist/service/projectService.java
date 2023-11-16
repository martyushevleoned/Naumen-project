package com.example.todolist.service;

import com.example.todolist.model.dto.ProjectDto;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Long addProject(User user, String projectName) {

        Project project = new Project();
        project.setName(projectName);
        project.setUser(user);
        Date date = new Date();
        project.setCreationDateTime(date);
        projectRepository.save(project);

        return projectRepository.findByUserIdAndDatetime(user, projectName, date).get(0).getId();
    }

    public Iterable<ProjectDto> getAllProjects(User user) {
        List<ProjectDto> projectDtos = new ArrayList<>();

        userRepository.getReferenceById(user.getId()).getProjects().forEach(p -> {
            projectDtos.add(new ProjectDto(p.getName(), p.getTasks().size()));
        });

        return projectDtos;
    }
}

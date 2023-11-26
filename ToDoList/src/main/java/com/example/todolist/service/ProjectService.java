package com.example.todolist.service;

import com.example.todolist.model.dto.projectPage.MemberDto;
import com.example.todolist.model.dto.projectPage.MessageDto;
import com.example.todolist.model.dto.projectPage.ProjectDto;
import com.example.todolist.model.dto.projectPage.TaskDto;
import com.example.todolist.model.dto.projectsListPage.ProjectCardDto;
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
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Iterable<ProjectCardDto> getAllProjects(User user) {
        List<ProjectCardDto> projectCardDtos = new ArrayList<>();

        userRepository.getReferenceById(user.getId()).getProjects().forEach(p -> {
            projectCardDtos.add(new ProjectCardDto(p.getId(), p.getName(), p.getTasks().size()));
        });

        return projectCardDtos;
    }

    public Long addProject(User user, String projectName) {

        Date date = new Date();

        Project project = new Project();
        project.setName(projectName);
        project.setUser(user);
        project.setCreationDateTime(date);
        projectRepository.save(project);

        return projectRepository.findByUserAndNameAndDatetime(user, projectName, date).get(0).getId();
    }

    public void deleteProject(User user, Long id) {

        projectRepository.findById(id).ifPresent(p -> {
            if (Objects.equals(p.getUser().getUsername(), user.getUsername()))
                projectRepository.delete(p);
        });
    }
}

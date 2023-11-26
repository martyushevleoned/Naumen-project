package com.example.todolist.service;

import com.example.todolist.model.dto.projectPage.MemberDto;
import com.example.todolist.model.dto.projectPage.MessageDto;
import com.example.todolist.model.dto.projectPage.ProjectDto;
import com.example.todolist.model.dto.projectPage.TaskDto;
import com.example.todolist.model.dto.projectsListPage.ProjectCardDto;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MemberRepository;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DtoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    public ProjectDto getMyProject(User user, Long id) {

        Optional<Project> projectDB = projectRepository.findById(id);
        if (projectDB.isEmpty())
            return null;

        Project project = projectDB.get();

        List<TaskDto> tasks = new ArrayList<>();
        project.getTasks().forEach(t -> {
            tasks.add(new TaskDto(t.getId(), t.getText()));
        });

        List<MessageDto> messages = new ArrayList<>();
        project.getMessages().forEach(m -> {
            messages.add(new MessageDto(m.getId(), m.getText()));
        });
        Collections.reverse(messages);

        List<MemberDto> members = new ArrayList<>();
        project.getMembers().forEach(m -> {
            members.add(new MemberDto(m.getId(), m.getUser().getUsername()));
        });

        Boolean isOwner = (Objects.equals(projectDB.get().getUser().getUsername(), user.getUsername()));

        return new ProjectDto(
                project.getId(),
                project.getName(),
                tasks,
                messages,
                members,
                project.getUser().getUsername(),
                isOwner
        );
    }

    public Iterable<ProjectCardDto> getMyProjects(User user) {
        List<ProjectCardDto> projectCardDtos = new ArrayList<>();

        userRepository.getReferenceById(user.getId()).getProjects().forEach(p -> {
            projectCardDtos.add(new ProjectCardDto(p.getId(), p.getName(), p.getTasks().size()));
        });

        return projectCardDtos;
    }

    public Iterable<ProjectCardDto> getForeignProjects(User user) {
        List<ProjectCardDto> projectCardDtos = new ArrayList<>();

        memberRepository.findByUser(user).forEach(m -> {
            projectCardDtos.add(new ProjectCardDto(m.getProject().getId(), m.getProject().getName(), m.getProject().getTasks().size()));
        });

        return projectCardDtos;
    }
}

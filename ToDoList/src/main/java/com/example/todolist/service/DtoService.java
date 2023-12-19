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
import com.example.todolist.service.entityService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DtoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ValidationService validationService;

    public ProjectDto getProject(User user, Long projectId) {

        if (!validationService.haveAccess(user.getUsername(), projectId))
            return null;

        Project project = projectRepository.getReferenceById(projectId);

        List<TaskDto> tasks = new ArrayList<>();
        project.getTasks().forEach(t -> {
            tasks.add(new TaskDto(t.getId(), t.getText(), t.getCreationDateTime()));
        });
        tasks.sort(Comparator.comparing(TaskDto::getCreationDateTime));

        List<MessageDto> messages = new ArrayList<>();
        project.getMessages().forEach(m -> {
            messages.add(new MessageDto(m.getId(), m.getText(), m.getCreationDateTime()));
        });
        messages.sort(Comparator.comparing(MessageDto::getCreationDateTime));

        List<MemberDto> members = new ArrayList<>();
        project.getMembers().forEach(m -> {
            members.add(new MemberDto(m.getUser().getId(), m.getUser().getUsername()));
        });
        members.sort(Comparator.comparing(MemberDto::getUsername));

        Boolean isOwner = project.getUser().equals(user);

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
            projectCardDtos.add(new ProjectCardDto(p.getId(), p.getName(), p.getTasks().size(), p.getCreationDateTime()));
        });
        projectCardDtos.sort(Comparator.comparing(ProjectCardDto::getCreationDateTime));

        return projectCardDtos;
    }

    public Iterable<ProjectCardDto> getForeignProjects(User user) {
        List<ProjectCardDto> projectCardDtos = new ArrayList<>();

        memberRepository.findByUser(user).forEach(m -> {
            projectCardDtos.add(new ProjectCardDto(m.getProject().getId(), m.getProject().getName(), m.getProject().getTasks().size(), m.getProject().getCreationDateTime()));
        });
        projectCardDtos.sort(Comparator.comparing(ProjectCardDto::getCreationDateTime));

        return projectCardDtos;
    }
}

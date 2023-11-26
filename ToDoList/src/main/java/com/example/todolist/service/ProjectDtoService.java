package com.example.todolist.service;

import com.example.todolist.model.dto.projectPage.MemberDto;
import com.example.todolist.model.dto.projectPage.MessageDto;
import com.example.todolist.model.dto.projectPage.ProjectDto;
import com.example.todolist.model.dto.projectPage.TaskDto;
import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectDtoService {

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

    public ProjectDto getProjectInfo(User user, Long id) {

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

        return new ProjectDto(
                project.getId(),
                project.getName(),
                tasks,
                messages,
                members,
                project.getUser().getUsername()
        );
    }
}

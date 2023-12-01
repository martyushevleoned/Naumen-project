package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.MemberId;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MemberRepository;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectService projectService;

    public Long addMember(User user, Long projectId, String username) {

//        Проверка на доступ к проекту и существование сущностей
        if (!projectService.haveAccess(user.getUsername(), projectId))
            return 0L;

        User userDB = userRepository.findByUsername(user.getUsername());
        Project projectDB = projectRepository.getReferenceById(projectId);

//        Проверка на существование добавляемого пользователя
        Optional<User> addUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (addUser.isEmpty())
            return 0L;

//        Проверка на то что пользователь владелец проекта
        if (!userDB.equals(projectDB.getUser()))
            return 0L;

//        Проверка на то что пользователь уже добавлен
        if (memberRepository.existsById(new MemberId(addUser.get().getId(), projectDB.getId())))
            return 0L;

//        Проверка на попытку добавить себя в проект
        if (Objects.equals(userDB.getUsername(), username))
            return 0L;

//        Добавляем в бд запись
        Member member = new Member(
                addUser.get(),
                projectDB
        );
        memberRepository.save(member);

//        достаём из бд запись чтобы пихнуть в респонс id пользователя
        return memberRepository.getReferenceById(new MemberId(addUser.get().getId(), projectDB.getId())).getUser().getId();
    }

    public void deleteMember(User user, Long projectId) {
        Optional<Project> projectDB = projectService.projectAccess(user, projectId);

        projectDB.ifPresent(project -> {
            Member member = memberRepository.getReferenceById(new MemberId(user.getId(), projectDB.get().getId()));
            memberRepository.delete(member);
        });
    }
}

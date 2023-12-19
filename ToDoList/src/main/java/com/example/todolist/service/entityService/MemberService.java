package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.MemberId;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MemberRepository;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.model.repository.UserRepository;
import com.example.todolist.service.ValidationService;
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

    @Autowired
    private ValidationService validationService;

    public Long addMember(User user, Long projectId, String username) {

//        Проверка на доступ к проекту и существование сущностей
        if (!validationService.haveAccess(user.getUsername(), projectId))
            return null;

//        Достаём используемые сущности из бд
        User userDB = userRepository.findByUsername(user.getUsername());
        Project projectDB = projectRepository.getReferenceById(projectId);

//        Проверка на существование добавляемого пользователя
        Optional<User> addUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (addUser.isEmpty())
            return null;

//        Проверка на то что пользователь владелец проекта
        if (!userDB.equals(projectDB.getUser()))
            return null;

//        Проверка на то что пользователь уже добавлен
        if (memberRepository.existsById(new MemberId(addUser.get().getId(), projectDB.getId())))
            return null;

//        Проверка на попытку добавить себя в проект
        if (Objects.equals(userDB.getUsername(), username))
            return null;

//        Добавляем в бд запись
        Member member = new Member(
                addUser.get(),
                projectDB
        );
        memberRepository.save(member);

//        достаём из бд запись чтобы пихнуть в респонс id пользователя
        return memberRepository.getReferenceById(new MemberId(addUser.get().getId(), projectDB.getId())).getUser().getId();
    }

    public boolean deleteMember(User user, Long projectId) {

//        Проверка на доступ к проекту и существование сущностей
        if (!validationService.haveAccess(user.getUsername(), projectId))
            return false;

//        Достаём используемые сущности из бд
        User userDB = userRepository.findByUsername(user.getUsername());
        Project projectDB = projectRepository.getReferenceById(projectId);

        MemberId id = new MemberId(userDB.getId(), projectDB.getId());

//        Проверяем существование сущности в бд
        if (memberRepository.findById(id).isEmpty())
            return false;

//        Удаляем сущность из БД
        memberRepository.deleteById(id);
        return true;
    }
}

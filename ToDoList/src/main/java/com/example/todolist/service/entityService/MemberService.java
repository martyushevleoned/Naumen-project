package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MemberRepository;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectService projectService;

    public Long addMember(User user, Long projectId, String username) {

//        Проверка на существование проекта
        Optional<Project> projectDB = projectService.projectAccess(user, projectId);
        if (projectDB.isEmpty())
            return 0L;

//        Проверка на то что пользователь владелец проекта
        if (!user.equals(projectDB.get().getUser()))
            return 0L;

//        Проверка на существование добавляемого пользователя
        Optional<User> userDB = Optional.ofNullable(userRepository.findByUsername(username));
        if (userDB.isEmpty())
            return 0L;

//        Проверка на то что пользователь уже добавлен
        if (memberRepository.findByUserAndProject(userDB.get(), projectDB.get()).size() != 0)
            return 0L;

//        Проверка на попытку добавить себя в проект
        if (Objects.equals(user.getUsername(), username))
            return 0L;

        Member member = new Member(
                userDB.get(),
                projectDB.get()
        );
        memberRepository.save(member);

        return memberRepository.findByUserAndProject(userDB.get(), projectDB.get()).get(0).getUser().getId();
    }

    public void deleteMember(User user, Long projectId) {
        Optional<Project> projectDB = projectService.projectAccess(user, projectId);
        projectDB.ifPresent(project -> {
            List<Member> members = memberRepository.findByUserAndProject(user, projectDB.get());

            if (members.size() == 1)
                memberRepository.delete(members.get(0));
        });
    }
}

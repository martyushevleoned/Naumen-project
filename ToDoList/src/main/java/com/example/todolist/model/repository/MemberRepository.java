package com.example.todolist.model.repository;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.user = ?1 AND m.project = ?2")
    List<Member> findByUserAndProject(User user, Project project);
}

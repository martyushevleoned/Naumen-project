package com.example.todolist.model.repository;

import com.example.todolist.model.entity.Member;
import com.example.todolist.model.entity.MemberId;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

    List<Member> findByUser(User user);
}

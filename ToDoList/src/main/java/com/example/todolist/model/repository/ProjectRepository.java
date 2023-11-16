package com.example.todolist.model.repository;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    @Query("SELECT p FROM Project p WHERE p.user = ?1 AND p.name = ?2 AND p.creationDateTime = ?3")
    List<Project> findByUserIdAndDatetime(User user, String name, Date creationDateTime);
}

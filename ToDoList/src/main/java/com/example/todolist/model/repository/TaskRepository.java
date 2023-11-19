package com.example.todolist.model.repository;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import com.example.todolist.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.project = ?1 AND t.text = ?2 AND t.creationDateTime = ?3")
    List<Task> findByProjectAndTextAndDatetime(Project project, String text, Date creationDateTime);
}

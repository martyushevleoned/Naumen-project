package com.example.todolist.model.repository;

import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {
    @Query("SELECT t.id FROM Task t WHERE t.project = ?1 AND t.text = ?2 AND t.creationDateTime = ?3  ORDER BY t.id LIMIT 1")
    Long findIdByProjectAndTextAndDatetime(Project project, String text, Date creationDateTime);
}

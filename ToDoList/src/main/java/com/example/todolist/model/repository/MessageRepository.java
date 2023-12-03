package com.example.todolist.model.repository;

import com.example.todolist.model.entity.Message;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m.id FROM Message m WHERE m.user = ?1 AND m.text = ?2 AND m.project = ?3 AND m.creationDateTime = ?4 ORDER BY m.id LIMIT 1")
    Long findIdByUserAndTextAndProjectAndDatetime(User user, String text, Project project, Date creationDateTime);
}

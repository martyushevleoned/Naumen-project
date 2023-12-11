package com.example.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "USR_CHT_FK"), nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "chatId", foreignKey = @ForeignKey(name = "CHT_FK"), nullable = false)
    private Project project;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date creationDateTime;

    public Message(User user, String text, Project project, Date creationDateTime) {
        this.user = user;
        this.text = text;
        this.project = project;
        this.creationDateTime = creationDateTime;
    }
}
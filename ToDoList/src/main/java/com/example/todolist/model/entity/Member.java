package com.example.todolist.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "MEM_USR_FK"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatId", foreignKey = @ForeignKey(name = "MEM_PRJ_FK"))
    private Project project;
}
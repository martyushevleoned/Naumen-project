package com.example.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ownerId", foreignKey = @ForeignKey(name = "USR_CHT_FK"))
    private User user;

    @OneToMany(mappedBy = "project")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<Task> Tasks = new ArrayList<>();
}
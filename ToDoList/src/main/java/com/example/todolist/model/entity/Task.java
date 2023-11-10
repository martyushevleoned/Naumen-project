package com.example.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "ProjectId", foreignKey = @ForeignKey(name = "PRJ_TSK_FK"))
    private Project project;

    public Task(String title, String text, Project project) {
        this.title = title;
        this.text = text;
        this.project = project;
    }
}
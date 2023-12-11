package com.example.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date creationDateTime;
    @ManyToOne
    @JoinColumn(name = "ProjectId", foreignKey = @ForeignKey(name = "PRJ_TSK_FK"), nullable = false)
    private Project project;

    public Task(String text, Date creationDateTime, Project project) {
        this.text = text;
        this.creationDateTime = creationDateTime;
        this.project = project;
    }
}
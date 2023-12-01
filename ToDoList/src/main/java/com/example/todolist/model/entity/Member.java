package com.example.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@IdClass(MemberId.class)
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "MEM_USR_FK"))
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "projectId", foreignKey = @ForeignKey(name = "MEM_PRJ_FK"))
    private Project project;

    public Member(User user, Project project) {
        this.user = user;
        this.project = project;
    }
}
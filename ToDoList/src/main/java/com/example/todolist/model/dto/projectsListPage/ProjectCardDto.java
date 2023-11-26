package com.example.todolist.model.dto.projectsListPage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ProjectCardDto {

    Long id;
    String name;
    int countOfTasks;
    Date creationDateTime;

}

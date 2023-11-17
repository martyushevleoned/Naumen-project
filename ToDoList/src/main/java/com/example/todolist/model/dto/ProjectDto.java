package com.example.todolist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectDto {

    Long id;
    String name;
    int countOfTasks;

}

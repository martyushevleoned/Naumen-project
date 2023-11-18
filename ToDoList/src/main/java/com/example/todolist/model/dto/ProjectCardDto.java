package com.example.todolist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectCardDto {

    Long id;
    String name;
    int countOfTasks;

}

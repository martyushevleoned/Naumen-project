package com.example.todolist.model.dto.projectPage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectDto {

    Long id;

    Iterable<TaskDto> tasks;

    Iterable<MemberDto> members;

    Iterable<MessageDto> messages;
}

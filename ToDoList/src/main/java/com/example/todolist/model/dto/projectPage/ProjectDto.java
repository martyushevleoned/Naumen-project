package com.example.todolist.model.dto.projectPage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectDto {

    Long id;

    String name;

    Iterable<TaskDto> tasks;

    Iterable<MessageDto> messages;

    Iterable<MemberDto> members;

    String ownerName;

    Boolean isOwner;
}

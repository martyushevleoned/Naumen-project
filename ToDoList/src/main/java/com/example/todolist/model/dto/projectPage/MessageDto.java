package com.example.todolist.model.dto.projectPage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MessageDto {

    Long id;

    String text;

    Date creationDateTime;
}

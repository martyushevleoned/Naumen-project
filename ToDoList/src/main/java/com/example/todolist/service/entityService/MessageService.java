package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Message;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MessageRepository;
import com.example.todolist.model.repository.ProjectRepository;
import com.example.todolist.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationService validationService;

    public Long addMessage(User user, Long projectId, String text){

//        Проверка на доступ к проекту и существование сущностей
        if (!validationService.haveAccess(user.getUsername(), projectId))
            return null;

        Project project = projectRepository.getReferenceById(projectId);

//        Создание и сохранение сообщения в бд
        Date date = new Date();
        Message message = new Message(user, text, project, date);
        messageRepository.save(message);

//        Получение id сообщения из бд
        return messageRepository.findIdByUserAndTextAndProjectAndDatetime(user, text, project, date);
    }
}

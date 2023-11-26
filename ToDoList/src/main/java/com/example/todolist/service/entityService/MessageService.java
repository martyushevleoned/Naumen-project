package com.example.todolist.service.entityService;

import com.example.todolist.model.entity.Message;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ProjectService projectService;

    public Long addMessage(User user, Long projectId, String text){

        Optional<Project> projectDB = projectService.projectAccess(user, projectId);
        if (projectDB.isEmpty())
            return 0L;

        Project project = projectDB.get();

        Date date = new Date();
        Message message = new Message(user, text, project, date);
        messageRepository.save(message);

        return messageRepository.findByUserAndTextAndProjectAndDatetime(user, text, project, date).get(0).getId();
    }
}

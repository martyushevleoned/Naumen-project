package com.example.todolist.service;

import com.example.todolist.model.entity.Message;
import com.example.todolist.model.entity.Project;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.MessageRepository;
import com.example.todolist.model.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccessService accessService;

    public Long addMessage(User user, Long projectId, String text){

        if(!accessService.haveAccess(user,projectId))
            return 0L;

        Optional<Project> projectDB = projectRepository.findById(projectId);
        if (projectDB.isEmpty())
            return 0L;
        Project project = projectDB.get();


        Date date = new Date();
        Message message = new Message();
        message.setUser(user);
        message.setText(text);
        message.setProject(project);
        message.setCreationDateTime(date);
        messageRepository.save(message);

        return messageRepository.findByUserAndTextAndProjectAndDatetime(user, text, project, date).get(0).getId();
    }
}

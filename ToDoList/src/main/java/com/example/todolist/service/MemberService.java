package com.example.todolist.service;

import com.example.todolist.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public Boolean addMember(User user, Long projectId, String username){
        return true;
    }
}

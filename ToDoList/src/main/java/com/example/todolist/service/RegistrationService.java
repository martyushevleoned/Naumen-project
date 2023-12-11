package com.example.todolist.service;

import com.example.todolist.model.entity.Role;
import com.example.todolist.model.entity.User;
import com.example.todolist.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean userDataIsCorrect(String username, String password) {

        int minTextLength = 2;
        int maxTextLength = 40;

        if (minTextLength > username.length() ||  username.length() > maxTextLength)
            return false;

        if (minTextLength > password.length() || password.length() > maxTextLength)
            return false;

        return true;
    }
    public boolean isUserExist(User user) {

        User userDb = userRepository.findByUsername(user.getUsername());
        return userDb != null;
    }

    public void saveUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
package com.example.demo.service;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    void save(UserRegistrationDto userDto);
    User findByEmail(String email);
    void addTask(Task task, User user);
    List<Task> findAllUserTask(User user);
   List<User> findAll();
    void deleteAll();
    List<Task> deleteTask(String description, User user);
    void update(User user);
    boolean isUserPresent(String email);
}

package com.example.demo.service;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserRegistrationDto userDto) {
        List<Task> tasks = new ArrayList<>();
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setTaskList(tasks);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addTask(Task task, User user) {
        user.getTaskList().add(task);
        userRepository.save(user);
    }

    @Override
    public List<Task> findAllUserTask(User user) {
        return userRepository.findByEmail(user.getEmail()).getTaskList();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<Task> deleteTask(String description, User user) {
        List<Task> taskList = user.getTaskList();
        Task task = new Task();
        task.setDescription(description);
        taskList.remove(task);
        user.setTaskList(taskList);
        return userRepository.save(user).getTaskList();
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean isUserPresent(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}

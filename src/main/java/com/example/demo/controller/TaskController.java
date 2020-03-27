package com.example.demo.controller;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(value = "task")
public class TaskController {
    private final UserService userService;

    public TaskController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String list(Model model, Principal principal) {
        List<Task> tasks = userService.findByEmail(principal.getName()).getTaskList();
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/addTask")
    public String taskForm(Model model) {
        model.addAttribute("task", new Task());
        return "taskForm";
    }

    @PostMapping("/addTask")
    public String addTaskPost(Task task, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "list";
        }
        String email = principal.getName();
        userService.addTask(task, userService.findByEmail(email));
        return "redirect:/list";
    }

    @GetMapping("/deleteTask")
    public String deleteTask(@RequestParam String task, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Task> taskList = userService.deleteTask(task, user);
        model.addAttribute("tasks", taskList);
        return "list";
    }

    @GetMapping("/editTask")
    public String editTask(@RequestParam String task, Model model, Principal principal) {
        Task toDo = new Task();
        toDo.setDescription(task);
        userService.deleteTask(task, userService.findByEmail(principal.getName()));
        model.addAttribute("task", toDo);
        return "edit";
    }

    @PostMapping("/editTask")
    public String editTaskPost(@ModelAttribute TaskDto taskDto, Principal principal, Model model) {
        Task toDo = new Task();
        toDo.setDescription(taskDto.getDescription());
        User user = userService.findByEmail(principal.getName());
        user.getTaskList().add(toDo);
        userService.update(user);
        model.addAttribute("tasks", user.getTaskList());
        return "list";
    }
}

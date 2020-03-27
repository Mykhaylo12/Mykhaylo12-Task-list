package com.example.demo.model;

import lombok.Data;
import java.util.List;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private List<Task> taskList;
}

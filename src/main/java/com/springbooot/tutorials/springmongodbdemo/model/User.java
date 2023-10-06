package com.springbooot.tutorials.springmongodbdemo.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "user")
@Data
@RequiredArgsConstructor
public class User {

    @Id
    private String username;

    private String password;

    private String mobileNo;

    private String emailId;

    private String authorities;
}

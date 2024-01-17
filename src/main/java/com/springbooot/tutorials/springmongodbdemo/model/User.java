package com.springbooot.tutorials.springmongodbdemo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(value = "user")
@Data
@RequiredArgsConstructor
public class User {

    @Id
    @Field(value = "username")
    @NotBlank(message = "Username Should Not be Empty")
    private String username;

    @NotBlank(message = "Password Should Not be Empty")
    private String password;

    @NotBlank(message = "MobileNo Should Not be Empty")
    private String mobileNo;

    @NotBlank(message = "EmailId Should Not be Empty")
    @Email
    private String emailId;

    @NotBlank(message = "Authorities Should Not be Empty")
    private String authorities;
}

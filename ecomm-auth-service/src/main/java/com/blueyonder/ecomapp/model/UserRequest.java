package com.blueyonder.ecomapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserRequest
{
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    @Id
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean active;
}
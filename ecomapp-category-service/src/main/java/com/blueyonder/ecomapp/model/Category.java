package com.blueyonder.ecomapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.LinkedHashSet;

@Document(collection = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category
{
    @Transient
    public static final String SEQUENCE_NAME = "contacts_sequence";
    @Id
    private int id;
    
    @NotBlank(message = "Category Name is required")
    private String name;

    @NotBlank(message = "Category Description is required")
    private String description;
}

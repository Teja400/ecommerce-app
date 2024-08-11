package com.blueyonder.ecomapp.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product
{
    @Transient
    public static final String SEQUENCE_NAME = "contacts_sequence";
    @Id
    private int id;
    @NotBlank(message = "Product Name is required")
    private String name;
    @Positive(message = "Product Price should be positive")
    @NotNull(message = "Product Price is required")
    private double price;
    @NotBlank(message = "Product Description is required")
    private String description;
    @NotNull(message = "Product Category is required")
    private int categoryId;
}

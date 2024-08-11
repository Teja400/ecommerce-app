package com.blueyonder.ecomapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("database_sequences_products")
@Data
public class DatabaseSequences {

    @Id
    private String id;
    private Integer seq;
}

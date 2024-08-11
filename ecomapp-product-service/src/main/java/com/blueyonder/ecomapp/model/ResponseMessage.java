package com.blueyonder.ecomapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage
{
    private Integer id;
    private String status;
    private Integer statusCode;
    private String message;
}

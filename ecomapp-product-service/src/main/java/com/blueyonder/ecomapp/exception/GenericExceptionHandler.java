package com.blueyonder.ecomapp.exception;

import com.blueyonder.ecomapp.model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.blueyonder.ecomapp")
public class GenericExceptionHandler
{
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseMessage> handleException(Exception e)
    {
        return ResponseEntity.internalServerError().body(getErrorResponse(-1, "ERROR: " + e.getMessage()));
    }

    @ExceptionHandler({ProductException.class})
    public ResponseEntity<ResponseMessage> handleProductException(ProductException e)
    {
        return ResponseEntity.internalServerError().body(getErrorResponse(-1, "ProductException: " + e.getMessage()));
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ResponseMessage> handleProductNotFoundException(ProductNotFoundException e)
    {
        return ResponseEntity.internalServerError().body(getErrorResponse(-1, "ProductNotFoundException: " + e.getMessage()));
    }

     private ResponseMessage getErrorResponse(Integer id, String message) {
        ResponseMessage response = new ResponseMessage();
        response.setId(id);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        ResponseMessage response = new ResponseMessage(-1, HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), "Validation Error!!! \n" + e.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.joining(", \n")));
//        ResponseMessage response = new ResponseMessage("Failure", "Validation Error!!! \n" + e.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.joining(", \n")));
//        return ResponseEntity.badRequest().body(response);
        return ResponseEntity.badRequest().body(response);
    }
}

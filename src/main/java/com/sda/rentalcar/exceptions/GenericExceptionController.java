package com.sda.rentalcar.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericExceptionController {
@ExceptionHandler(GenericException.class)
    public ResponseEntity<String>exceptionHandler(GenericException genericException){
    return ResponseEntity.status(genericException.getStatus()).body(genericException.getMessage());
}
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>argumentNotValid(MethodArgumentNotValidException exception){
    String message = "";
 for (FieldError error:exception.getFieldErrors()){
     if (error.getDefaultMessage() != null){
         message = message.concat(error.getDefaultMessage()).concat(",");
     }
 }
    return ResponseEntity.status(exception.getStatusCode().value()).body(message);
}
}

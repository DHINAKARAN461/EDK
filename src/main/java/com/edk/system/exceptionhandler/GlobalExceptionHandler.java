package com.edk.system.exceptionhandler;

import com.edk.system.exception.CredentialException;
import com.edk.system.exception.LoginException;
import com.edk.system.exception.MailIdException;
import com.edk.system.exception.PhoneNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({PhoneNumberException.class, MailIdException.class})
    public ResponseEntity<String>handlerConflict(RuntimeException ex){
        log.info("not valid",ex.getMessage());
       return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String>handleNotFound(LoginException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>handleNotValidSpring(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<String>handleNotFound(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

}

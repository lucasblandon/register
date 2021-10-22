package com.register.register.controller;

import com.register.register.model.User;
import com.register.register.service.UserService;
import com.register.register.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UserController {

    @Autowired
    UserService userService;



    @PostMapping("/register")
    ResponseEntity<User>create(@Valid @RequestBody User user){
       // if(userService.findByEmail(user.getEmail())==null)
            return new ResponseEntity(userService.save(user), HttpStatus.OK);
        //else
          //  return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());

        return fieldErrorMessages;
    }

    @GetMapping("/register")
    Iterable<User> read(){
        return userService.findAll();
    }

    @GetMapping("/register/{id}")
    Optional<User> fiendById(@PathVariable Integer id){
        return userService.findById(id);
    }


    @PutMapping("/register")
    ResponseEntity<User> update(@RequestBody User user){
        if (userService.findById(user.getId()).isPresent()) {
            Date sc = new Date();
            user.getDateUpdate(sc);
            return new ResponseEntity(userService.save(user), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/register/{id}")
    void delete (@PathVariable Integer id){
        userService.deleteById(id);
    }




}

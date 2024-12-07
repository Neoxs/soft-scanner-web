package org.example.softscannerweb.controller;

import org.example.softscannerweb.SoftScannerWebApplication;
import org.example.softscannerweb.model.User;
import org.example.softscannerweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private static Logger userLogger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUserWithDetails(user);
            userLogger.info(String.format("Timestamp: %s, Event: User Creation, User: %s",
                    LocalDateTime.now(), createdUser));
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userLogger.info(String.format("Timestamp: %s, Event: User Retrieved, User: %s",
                    LocalDateTime.now(), user));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
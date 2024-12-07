package org.example.softscannerweb.controller;

import org.example.softscannerweb.model.Store;
import org.example.softscannerweb.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/store")
@CrossOrigin(origins = "*")
public class StoreController {

    private static Logger storeLogger = Logger.getLogger(StoreController.class.getName());

    @Autowired
    private StoreService storeService;

    @GetMapping("/get")
    public ResponseEntity<Store> getStore() {
        Store store = storeService.getStore();
        storeLogger.info(String.format("Timestamp: %s, Event: Store Retrieved, User: %s",
                LocalDateTime.now(), "system"));
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    @PostMapping("/init")
    public ResponseEntity<Store> initStore() {
        storeService.initStore();
        Store store = storeService.getStore();
        storeLogger.info(String.format("Timestamp: %s, Event: Store Initialized, User: %s",
                LocalDateTime.now(), "system"));
        return new ResponseEntity<>(store, HttpStatus.OK);
    }
}
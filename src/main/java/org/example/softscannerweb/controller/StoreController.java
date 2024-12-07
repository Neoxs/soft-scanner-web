package org.example.softscannerweb.controller;


import org.example.softscannerweb.SoftScannerWebApplication;
import org.example.softscannerweb.model.Store;
import org.example.softscannerweb.services.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/store")
@CrossOrigin(origins = "*")
public class StoreController {

    private static StoreService storeService = new StoreService();
    private static Logger StoreLogger = Logger.getLogger(StoreController.class.getName());
    private FileHandler fileHandler;

    public StoreController() {
        try {
            this.fileHandler = new FileHandler("StoreController.log", true);
            StoreLogger.addHandler(this.fileHandler);
        } catch (SecurityException | IOException e) {
            StoreLogger.severe("Impossible to open FileHandler");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Store> getStore() {
        Store store = new Store();
        storeService.getStore(store);
        StoreLogger.info(String.format("Timestamp: %s, Event: Store Retrieved, User: %s",
                LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser()));
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    @PostMapping("/init")
    public ResponseEntity<Store> initStore() {
        Store store = new Store();
        storeService.initStore(store);
        StoreLogger.info(String.format("Timestamp: %s, Event: Store Initialized, User: %s",
                LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser()));
        return new ResponseEntity<>(store, HttpStatus.OK);
    }
}
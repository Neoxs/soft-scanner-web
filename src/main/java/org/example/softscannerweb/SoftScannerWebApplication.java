package org.example.softscannerweb;

import org.example.softscannerweb.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@SpringBootApplication
public class SoftScannerWebApplication {
    private static Logger LOGGER = Logger.getLogger(SoftScannerWebApplication.class.getName());
    private static FileHandler fh;

    static {
        try {
            fh = new FileHandler("SoftScannerApplication.log", true);
            LOGGER.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LOGGER.addHandler(fh);
        SpringApplication.run(SoftScannerWebApplication.class, args);
    }
}

package org.example.softscannerweb.controller;

import org.example.softscannerweb.SoftScannerWebApplication;
import org.example.softscannerweb.exception.ProductAlreadyExistsException;
import org.example.softscannerweb.exception.ProductNotFoundException;
import org.example.softscannerweb.model.Product;
import org.example.softscannerweb.model.Store;
import org.example.softscannerweb.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private static ProductService productService = new ProductService();
    private static Logger productLogger = Logger.getLogger(ProductController.class.getName());
    private FileHandler fileHandler;

    public ProductController() {
        try {
            this.fileHandler = new FileHandler("ProductController.log", true);
            productLogger.addHandler(this.fileHandler);
        } catch (SecurityException | IOException e) {
            productLogger.severe("Error initializing FileHandler");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Store store = new Store();
        try {
            Product createdProduct = productService.createProductWithDetails(store, product);
            productLogger.info(String.format("Timestamp: %s, Event: Product Creation, User: %s, Product: %s",
                    LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser(), createdProduct));
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (ProductAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Store store = new Store();
        try {
            Product product = productService.getProductById(store, id);
            productLogger.info(String.format("Timestamp: %s, Event: Product Retrieval, User: %s, Product: %s",
                    LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser(), product));
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        Store store = new Store();
        productService.initializeDefaultProducts(store);
        List<Product> products = store.getProducts();
        productLogger.info(String.format("Timestamp: %s, Event: All Products Retrieved, User: %s",
                LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser()));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        Store store = new Store();
        try {
            productService.deleteProductById(store, id);
            productLogger.info(String.format("Timestamp: %s, Event: Product Deletion, User: %s, ProductID: %s",
                    LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser(), id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Store store = new Store();
        try {
            Product updatedProduct = productService.updateProductWithDetails(store, id, product);
            productLogger.info(String.format("Timestamp: %s, Event: Product Update, User: %s, Product: %s",
                    LocalDateTime.now(), SoftScannerWebApplication.getCurrentUser(), updatedProduct));
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException | ProductAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
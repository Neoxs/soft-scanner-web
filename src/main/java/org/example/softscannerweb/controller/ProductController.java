package org.example.softscannerweb.controller;

import org.example.softscannerweb.exception.ProductAlreadyExistsException;
import org.example.softscannerweb.exception.ProductNotFoundException;
import org.example.softscannerweb.model.Product;
import org.example.softscannerweb.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private static Logger productLogger = Logger.getLogger(ProductController.class.getName());

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProductWithDetails(product);
            productLogger.info(String.format("Timestamp: %s, Event: Product Creation, User: %s, Product: %s",
                    LocalDateTime.now(), createdProduct.getId(), createdProduct));
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (ProductAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        try {
            Product product = productService.getProductById(id);
            productLogger.info(String.format("Timestamp: %s, Event: Product Retrieval, User: %s, Product: %s",
                    LocalDateTime.now(), id, product));
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        productLogger.info(String.format("Timestamp: %s, Event: All Products Retrieved, User: %s",
                LocalDateTime.now(), "system"));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProductById(id);
            productLogger.info(String.format("Timestamp: %s, Event: Product Deletion, User: %s, ProductID: %s",
                    LocalDateTime.now(), "system", id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProductWithDetails(id, product);
            productLogger.info(String.format("Timestamp: %s, Event: Product Update, User: %s, Product: %s",
                    LocalDateTime.now(), id, updatedProduct));
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
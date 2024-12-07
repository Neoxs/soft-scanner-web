package org.example.softscannerweb.services;

import org.example.softscannerweb.exception.ProductAlreadyExistsException;
import org.example.softscannerweb.exception.ProductNotFoundException;
import org.example.softscannerweb.model.Product;
import org.example.softscannerweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreService storeService;

    public Product createProductWithDetails(Product product) throws ProductAlreadyExistsException {
        if (productRepository.existsById(product.getID())) {
            throw new ProductAlreadyExistsException("Product with ID " + product.getID() + " already exists");
        }
        Product savedProduct = productRepository.save(product);
        storeService.addProductToStore(savedProduct);
        return savedProduct;
    }

    public Product updateProductWithDetails(String id, Product product)
            throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        product.setID(id);
        return productRepository.save(product);
    }

    public void deleteProductById(String id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
        storeService.removeProductFromStore(id);
    }

    public Product getProductById(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
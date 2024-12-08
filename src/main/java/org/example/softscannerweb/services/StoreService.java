package org.example.softscannerweb.services;


import jakarta.annotation.PostConstruct;
import org.example.softscannerweb.exception.ProductAlreadyExistsException;
import org.example.softscannerweb.exception.ProductNotFoundException;
import org.example.softscannerweb.model.Product;
import org.example.softscannerweb.model.Store;
import org.example.softscannerweb.repository.ProductRepository;
import org.example.softscannerweb.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void init() {
        if (getStore() == null) {
            Store store = new Store();
            store.setId("main-store");
            store.setProducts(new ArrayList<>());
            storeRepository.save(store);
        }
    }

    public Store getStore() {
        return storeRepository.findFirstByOrderByIdAsc();
    }

    public void initStore() {
        Store store = getStore();
        if (store == null) {
            store = new Store();
            store.setId("main-store");
            store.setProducts(new ArrayList<>());
        }

        // Clear existing products
        productRepository.deleteAll();
        store.getProducts().clear();

        // Create new sample products
        try {
            addSampleProducts(store);
        } catch (ProductAlreadyExistsException e) {
            e.printStackTrace();
        }

        storeRepository.save(store);
    }

    private void addSampleProducts(Store store) throws ProductAlreadyExistsException {
        Product[] products = {
                new Product("101", "Smartwatch Pro X1 Series", "299", "12/31/24"),
                new Product("102", "Wireless Earbuds Elite", "159", "None"),
                new Product("103", "Portable Power Bank 20000mAh", "49", "06/15/25"),
                new Product("104", "Bluetooth Speaker Waterproof", "89", "None"),
                new Product("105", "HD Webcam 1080p", "79", "None")
        };

        for (Product product : products) {
            Product savedProduct = productRepository.save(product);
            store.getProducts().add(savedProduct);
        }
    }

    public void addProductToStore(Product product) {
        Store store = getStore();
        if (!store.getProducts().contains(product)) {
            store.getProducts().add(product);
            storeRepository.save(store);
        }
    }

    public void removeProductFromStore(String productId) {
        Store store = getStore();
        store.getProducts().removeIf(p -> p.getId().equals(productId));
        storeRepository.save(store);
    }
}
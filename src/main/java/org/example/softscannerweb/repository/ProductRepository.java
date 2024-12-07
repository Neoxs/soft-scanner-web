package org.example.softscannerweb.repository;

import org.example.softscannerweb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
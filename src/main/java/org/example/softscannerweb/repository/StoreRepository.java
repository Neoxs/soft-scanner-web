package org.example.softscannerweb.repository;

import org.example.softscannerweb.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store, String> {
    Store findFirstByOrderByIdAsc();
}
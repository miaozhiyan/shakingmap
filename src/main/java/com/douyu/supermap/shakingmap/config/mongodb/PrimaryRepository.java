package com.douyu.supermap.shakingmap.config.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
package com.peter_struschka.cardemo.query;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarViewRepository extends MongoRepository<CarView, String> {
}

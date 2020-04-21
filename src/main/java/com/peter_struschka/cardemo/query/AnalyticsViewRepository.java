package com.peter_struschka.cardemo.query;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnalyticsViewRepository extends MongoRepository<AnalyticsView, String> {
    boolean existsByCarId(String carId);
    long countAllByType(StatusType type);
    long countByCarIdAndType(String carId, StatusType type);
    long countByCarId(String carId);
    List<AnalyticsView> findAllByOrderByTimestamp();
    List<AnalyticsView> findAllByTypeOrderByTimestamp(StatusType type);
    List<AnalyticsView> findByCarIdOrderByTimestamp(String carId);
    List<AnalyticsView> findByCarIdAndTypeOrderByTimestamp(String carId, StatusType type);
}

package com.peter_struschka.cardemo.query;

import com.peter_struschka.cardemo.coreapi.*;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AnalyticsProjector {

    private final AnalyticsViewRepository repository;

    @EventHandler
    public void on(CarCreatedEvent event, @Timestamp Instant timestamp) {
        repository.save(new AnalyticsView(event.getCarId(), timestamp, StatusType.CREATE, "Car '" + event.getCarId() + "' created"));
    }

    @EventHandler
    public void on(CarStartedEvent event, @Timestamp Instant timestamp) {
        repository.save(new AnalyticsView(event.getCarId(), timestamp, StatusType.START, "Car '" + event.getCarId() + "' started"));
    }

    @EventHandler
    public void on(CarStoppedEvent event, @Timestamp Instant timestamp) {
        repository.save(new AnalyticsView(event.getCarId(), timestamp, StatusType.STOP, "Car '" + event.getCarId() + "' stopped"));
    }

    @QueryHandler Iterable<AnalyticsView> handle(RetrieveAllCarsHistoryQuery query) {
        if (query.getType().isPresent()) {
            return repository.findAllByTypeOrderByTimestamp(query.getType().get());
        } else {
            return repository.findAllByOrderByTimestamp();
        }
    }

    @QueryHandler long handle(CountAllCarsHistoryQuery query) {
        if (query.getType().isPresent()) {
            return repository.countAllByType(query.getType().get());
        } else {
            return repository.count();
        }
    }

    @QueryHandler
    public Iterable<AnalyticsView> handle(RetrieveCarHistoryQuery query) {
        if (!repository.existsByCarId(query.getCarId())) throw new IllegalArgumentException("Car '" + query.getCarId() + "' does not exist!");

        if (query.getType().isPresent()) {
            return repository.findByCarIdAndTypeOrderByTimestamp(query.getCarId(), query.getType().get());
        } else {
            return repository.findByCarIdOrderByTimestamp(query.getCarId());
        }
    }

    @QueryHandler
    public long handle(CountCarHistoryQuery query) {
        if (!repository.existsByCarId(query.getCarId())) throw new IllegalArgumentException("Car " + query.getCarId() + " does not exist!");

        if (query.getType().isPresent()) {
            return repository.countByCarIdAndType(query.getCarId(), query.getType().get());
        } else {
            return repository.countByCarId(query.getCarId());
        }
    }
}

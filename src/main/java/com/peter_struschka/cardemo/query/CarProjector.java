package com.peter_struschka.cardemo.query;

import com.peter_struschka.cardemo.coreapi.*;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CarProjector {
    private final CarViewRepository repository;

    @EventHandler
    public void on(CarCreatedEvent event) {
        repository.save(new CarView(event.getCarId(), false));
    }

    @EventHandler
    public void on(CarStartedEvent event) {
        repository.findById(event.getCarId()).ifPresent(car -> {
            car.setStatus(true);
            repository.save(car);
        });
    }

    @EventHandler
    public void on(CarStoppedEvent event) {
        repository.findById(event.getCarId()).ifPresent(car -> {
            car.setStatus(false);
            repository.save(car);
        });
    }

    @QueryHandler
    public Iterable<CarView> handle(FindAllCarQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    public CarView handle(FindCarQuery query) {
        return repository.findById(query.getCarId()).orElseThrow(() ->
                new IllegalArgumentException("Car '" + query.getCarId() + "' does not exist!"));
    }
}

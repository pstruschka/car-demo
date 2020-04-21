package com.peter_struschka.cardemo.command;

import com.peter_struschka.cardemo.coreapi.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CarTest {

    private FixtureConfiguration<Car> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Car.class);
    }

    @Test
    public void createCar() {
        String carId = UUID.randomUUID().toString();
        fixture.givenNoPriorActivity()
                .when(new CreateCarCommand(carId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CarCreatedEvent(carId));
    }

    @Test
    public void startCar() {
        String carId = UUID.randomUUID().toString();
        fixture.given(new CarCreatedEvent(carId))
                .when(new StartCarCommand(carId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CarStartedEvent(carId));
    }

    @Test
    void stopCar() {
        String carId = UUID.randomUUID().toString();
        fixture.given(new CarCreatedEvent(carId), new CarStartedEvent(carId))
                .when(new StopCarCommand(carId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CarStoppedEvent(carId));
    }

    @Test
    void stopStoppedCar() {
        String carId = UUID.randomUUID().toString();
        fixture.given(new CarCreatedEvent(carId))
                .when(new StopCarCommand(carId))
                .expectException(IllegalStateException.class)
                .expectExceptionMessage("Car already stopped");
    }

    @Test
    void startStartedCar() {
        String carId = UUID.randomUUID().toString();
        fixture.given(new CarCreatedEvent(carId), new CarStartedEvent(carId))
                .when(new StartCarCommand(carId))
                .expectException(IllegalStateException.class)
                .expectExceptionMessage("Car already started");
    }
}
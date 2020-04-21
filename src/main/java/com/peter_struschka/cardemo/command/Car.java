package com.peter_struschka.cardemo.command;

import com.peter_struschka.cardemo.coreapi.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

@Slf4j
@NoArgsConstructor
@Aggregate
public class Car {

    @AggregateIdentifier
    private String carId;
    private boolean status;

    @CommandHandler
    public Car(CreateCarCommand command) {
        log.debug("Handling {}", command);
        apply(new CarCreatedEvent(command.getCarId()));
    }

    @CommandHandler
    public void handle(StartCarCommand command) {
        log.debug("Handling {}", command);
        if (this.status) {
            throw new IllegalStateException("Car already started");
        }
        apply(new CarStartedEvent(carId));
    }

    @CommandHandler
    public void handle(StopCarCommand command) {
        log.debug("Handling {}", command);
        if (!this.status) {
            throw new IllegalStateException("Car already stopped");
        }
        apply(new CarStoppedEvent(carId));
    }


    @EventSourcingHandler
    public void on(@NotNull CarCreatedEvent event) {
        log.debug("Applying {}", event);
        carId = event.getCarId();
        status = false;
    }

    @EventSourcingHandler
    public void on(@NotNull CarStartedEvent event) {
        log.debug("Car {} turned on", event.getCarId());
        status = true;
    }

    @EventSourcingHandler
    public void on(@NotNull CarStoppedEvent event) {
        log.debug("Car {} turned off", event.getCarId());
        status = false;
    }

}

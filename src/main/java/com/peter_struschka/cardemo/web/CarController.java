package com.peter_struschka.cardemo.web;

import com.peter_struschka.cardemo.coreapi.*;
import com.peter_struschka.cardemo.query.AnalyticsView;
import com.peter_struschka.cardemo.query.CarView;
import com.peter_struschka.cardemo.query.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/car")
public class CarController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @GetMapping()
    public CompletableFuture<List<CarView>> findAll() {
        return queryGateway.query(new FindAllCarQuery(), ResponseTypes.multipleInstancesOf(CarView.class));
    }

    @GetMapping("{carId}")
    public CompletableFuture<CarView> findOne(@PathVariable("carId") String carId) {
        return queryGateway.query(new FindCarQuery(carId), ResponseTypes.instanceOf(CarView.class));
    }

    @PutMapping
    public CompletableFuture<String> create() {
        return commandGateway.send(new CreateCarCommand(UUID.randomUUID().toString()));
    }

    @PutMapping("{carId}")
    public CompletableFuture<String> create(@PathVariable("carId") String carId) {
        return commandGateway.send(new CreateCarCommand(carId));
    }

    @PostMapping("{carId}/start")
    public CompletableFuture<String> start(@PathVariable("carId") String carId) {
        return commandGateway.send(new StartCarCommand(carId));
    }

    @PostMapping("{carId}/stop")
    public CompletableFuture<String> stop(@PathVariable("carId") String carId) {
        return commandGateway.send(new StopCarCommand(carId));
    }

    @GetMapping("/history")
    public CompletableFuture<List<AnalyticsView>> listAllHistory(@RequestParam Optional<StatusType> type) {
        log.debug("/history?type {}", type);
        return queryGateway.query(new RetrieveAllCarsHistoryQuery(type), ResponseTypes.multipleInstancesOf(AnalyticsView.class));
    }

    @GetMapping("/history/count")
    public CompletableFuture<Long> countAllHistory(@RequestParam Optional<StatusType> type) {
        log.debug("/history/count?type {}", type);
        return queryGateway.query(new CountAllCarsHistoryQuery(type), ResponseTypes.instanceOf(Long.class));
    }

    @GetMapping("{carId}/history")
    public CompletableFuture<List<AnalyticsView>> listCarHistory(@PathVariable("carId") String carId, @RequestParam("type") Optional<StatusType> type) {
        log.debug("/{}/history?type {}", carId, type);
        return queryGateway.query(new RetrieveCarHistoryQuery(carId, type), ResponseTypes.multipleInstancesOf(AnalyticsView.class));
    }

    @GetMapping("{carId}/history/count")
    public CompletableFuture<Long> countCarHistory(@PathVariable("carId") String carId, @RequestParam("type") Optional<StatusType> type) {
        log.debug("/{}/history/count?type {}", carId, type);
        return queryGateway.query(new CountCarHistoryQuery(carId, type), ResponseTypes.instanceOf(Long.class));
    }
}

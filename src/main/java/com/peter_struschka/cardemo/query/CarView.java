package com.peter_struschka.cardemo.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@Setter
@Document("car")
@TypeAlias("Car")
public class CarView {
    @Id
    private String carId;

    private boolean status;
}

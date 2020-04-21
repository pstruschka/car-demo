package com.peter_struschka.cardemo.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("car_analytics")
@TypeAlias("CarAnalytics")
public class AnalyticsView {
    private String carId;
    private Instant timestamp;

    private StatusType type;
    private String description;
}

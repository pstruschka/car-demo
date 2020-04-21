package com.peter_struschka.cardemo.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateCarCommand(@TargetAggregateIdentifier val carId: String)
data class StartCarCommand(@TargetAggregateIdentifier val carId: String)
data class StopCarCommand(@TargetAggregateIdentifier val carId: String)
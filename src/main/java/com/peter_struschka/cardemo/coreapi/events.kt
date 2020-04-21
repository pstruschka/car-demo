package com.peter_struschka.cardemo.coreapi

data class CarCreatedEvent(val carId: String)
data class CarStartedEvent(val carId: String)
data class CarStoppedEvent(val carId: String)
package com.peter_struschka.cardemo.coreapi

import com.peter_struschka.cardemo.query.StatusType
import java.util.*

data class FindCarQuery(val carId: String)

class FindAllCarQuery

data class RetrieveAllCarsHistoryQuery(val type: Optional<StatusType>)
data class RetrieveCarHistoryQuery(val carId: String, val type: Optional<StatusType>)
data class CountAllCarsHistoryQuery(val type: Optional<StatusType>)
data class CountCarHistoryQuery(val carId: String, val type: Optional<StatusType>)
package com.example.core_domain.model.common

sealed interface Status {

    object None : Status

    object InPlans : Status

    object Reading : Status

    class ReReading : Status

    class Completed : Status

    object Dropped : Status
}

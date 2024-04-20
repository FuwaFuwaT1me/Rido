package com.example.rido.navigation

interface Scenario {
    val name: String

    data object LibraryScenario : Scenario { override val name: String = "library_scenario" }
}
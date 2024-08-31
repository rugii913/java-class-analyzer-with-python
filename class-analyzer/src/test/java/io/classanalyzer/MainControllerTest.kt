package io.classanalyzer

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainControllerTest {

    private val controller: MainController = MainController()

    @Test
    fun control() {
        controller.control()
    }
}
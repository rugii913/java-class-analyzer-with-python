package io.analyzer;

import org.junit.jupiter.api.Test;

class MainControllerTest {

    MainController controller = new MainController();

    @Test
    public void controlTest() {
        controller.control();
    }
}
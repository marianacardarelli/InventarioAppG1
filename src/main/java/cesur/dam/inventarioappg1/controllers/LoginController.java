package cesur.dam.inventarioappg1.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

    /**
     * Al pulsar "Login" pasamos a la vista principal con la tabla de equipos.
     */
    @FXML
    public void login(ActionEvent actionEvent) {
        NavigationController.goTo(NavigationController.View.MAIN);
    }
}
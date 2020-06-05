package scs.iss.agentie.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import scs.iss.agentie.services.Service;


@Controller
public class RegisterController {
    private final ApplicationContext context;
    private final Service service;

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailTextField;

    public RegisterController(Service service, ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    public void showLogin(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void handleRegister(ActionEvent actionEvent) {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();

        service.registerUser(userName, password, email);
    }
}

package scs.iss.agentie.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import scs.iss.agentie.entities.User;
import scs.iss.agentie.entities.UserType;
import scs.iss.agentie.services.Service;

import javax.security.auth.login.LoginException;
import java.io.IOException;


@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginController {
    private final ApplicationContext context;
    private final Service service;

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;

    public LoginController(Service service, ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    public void handlerLogin(ActionEvent actionEvent) {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();

        try {
            User user = service.checkUser(userName, password);

            if (user.getUserType().equals(UserType.AGENT)) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getClassLoader().getResource("views/agentWindow.fxml"));
                    loader.setControllerFactory(context::getBean);
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    AgentController ctrl = loader.getController();
                    ctrl.setService(user);
                    stage.setScene(new Scene(root));
                    stage.show();
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getClassLoader().getResource("views/adminView.fxml"));
                    loader.setControllerFactory(context::getBean);
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    AdminController ctrl = loader.getController();
                    ctrl.setService();
                    stage.setScene(new Scene(root));
                    stage.show();
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (LoginException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
        }
    }

    public void showRegister(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("views/register.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            RegisterController ctrl = loader.getController();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package scs.iss.agentie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import scs.iss.agentie.controller.LoginController;

@SpringBootApplication
@EnableJpaRepositories(value = "scs.iss.agentie.repositories")
public class Main extends Application {
    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Main.class);
    }

    public void start(Stage primaryStage) throws Exception {
        for (int i = 0; i < 3; i++) {
            open();
        }
    }

    public void open() throws Exception{
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("views/login.fxml"));
        loader.setControllerFactory(springContext::getBean);
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        LoginController ctrl = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

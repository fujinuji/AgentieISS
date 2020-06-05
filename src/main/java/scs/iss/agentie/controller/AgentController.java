package scs.iss.agentie.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import scs.iss.agentie.entities.Product;
import scs.iss.agentie.entities.User;
import scs.iss.agentie.notify.Observable;
import scs.iss.agentie.services.Service;

import java.util.List;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AgentController implements Observable {
    private ObservableList<Product> productsModel = FXCollections.observableArrayList();

    private final Service service;
    private User currentUser;

    @FXML
    private TextField amountTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNamleColumn;
    @FXML
    private TableColumn<Product, String> availableAmountColumn;
    @FXML
    private TableColumn<Product, Float> priceColumn;

    public AgentController(Service service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        productNamleColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        availableAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(productsModel);
    }


    public void makeOrder(ActionEvent actionEvent) {
        Integer amount;
        try {
            amount = Integer.parseInt(amountTextField.getText());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
            return;
        }

        String customerName = customerNameTextField.getText();
        Product product = productTable.getSelectionModel().getSelectedItem();

        if(product == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Select a product first");
            a.show();
            return;
        }

        service.makeOrder(product.getId(), amount, customerName);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Order made");
        a.show();
    }

    public void setService(User agent) {
        this.currentUser = agent;
        this.productsModel.setAll(service.getProducts());
        service.addObservable(this);
    }

    @Override
    public void update(List<Product> products) {
        productsModel.setAll(products);
    }
}

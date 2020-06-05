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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import scs.iss.agentie.entities.Product;
import scs.iss.agentie.notify.Observable;
import scs.iss.agentie.services.Service;

import java.util.List;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdminController implements Observable {
    private final Service service;
    private ObservableList<Product> productsModel = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, String> amountColumn;
    @FXML
    private TableColumn<Product, Float> priceColumn;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField priceTextField;

    private Product currentProduct;

    public AdminController(Service service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.currentProduct = newSelection;

                productNameTextField.setText(newSelection.getProductName());
                amountTextField.setText(newSelection.getAmount().toString());
                priceTextField.setText(newSelection.getPrice().toString());
            }
        });

        productTable.setItems(productsModel);
    }

    public void handleAdd(ActionEvent actionEvent) {
        String productName = productNameTextField.getText();
        Integer amount = null;
        Float price = null;
        try {
            amount = Integer.parseInt(amountTextField.getText());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
            return;
        }
        try {
            price = Float.parseFloat(priceTextField.getText());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
            return;
        }

        service.addProduct(productName, amount, price);

    }

    public void setService() {
        productsModel.setAll(service.getProducts());
        service.addObservable(this);
    }

    public void handleUpdate(ActionEvent actionEvent) {
        if (currentProduct == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select a product first");
            a.show();
            return;
        }

        String productName = productNameTextField.getText();
        Integer amount = null;
        Float price = null;
        try {
            amount = Integer.parseInt(amountTextField.getText());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
            return;
        }
        try {
            price = Float.parseFloat(priceTextField.getText());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
            return;
        }
        service.updateProduct(currentProduct.getId(), productName, amount, price);
    }

    public void handleDelete(ActionEvent actionEvent) {
        Product product = productTable.getSelectionModel().getSelectedItem();

        if(product == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Select a product first");
            a.show();
            return;
        }

        service.deleteProduct(product.getId());
    }

    @Override
    public void update(List<Product> products) {
        productsModel.setAll(products);
    }
}

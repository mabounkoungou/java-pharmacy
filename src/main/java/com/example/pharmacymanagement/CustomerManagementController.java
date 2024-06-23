package com.example.pharmacymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

// Import the Customer and Purchase classes
import com.example.pharmacymanagement.Models.Customer;
import com.example.pharmacymanagement.Models.Purchase;

public class CustomerManagementController {

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerContactField;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerContactColumn;

    @FXML
    private TableView<Purchase> purchaseHistoryTable;

    @FXML
    private TableColumn<Purchase, String> drugIdColumn;

    @FXML
    private TableColumn<Purchase, Integer> quantityColumn;

    @FXML
    private TableColumn<Purchase, Double> totalAmountColumn;

    @FXML
    private TableColumn<Purchase, String> purchaseDateColumn;

    private ObservableList<Customer> customerData;

    private ObservableList<Purchase> purchaseHistoryData;

    public CustomerManagementController() {
        customerData = FXCollections.observableArrayList();
        purchaseHistoryData = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        customerTable.setItems(customerData);

        drugIdColumn.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));

        purchaseHistoryTable.setItems(purchaseHistoryData);
    }

    @FXML
    private void handleAddCustomer(ActionEvent event) {
        Customer customer = new Customer(customerIdField.getText(), customerNameField.getText(), customerContactField.getText());
        customerData.add(customer);
        clearFields();
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            selectedCustomer.setCustomerId(customerIdField.getText());
            selectedCustomer.setName(customerNameField.getText());
            selectedCustomer.setContact(customerContactField.getText());
            customerTable.refresh();
            clearFields();
        } else {
            showAlert("No Selection", "No Customer Selected", "Please select a customer in the table.");
        }
    }

    @FXML
    private void handleViewPurchaseHistory(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            // Populate purchaseHistoryData with the selected customer's purchase history
            // This is just an example, replace with actual data fetching logic
            purchaseHistoryData.clear();
            purchaseHistoryData.addAll(
                    new Purchase("D001", 2, 200.0, "2024-01-01"),
                    new Purchase("D002", 1, 100.0, "2024-02-01")
            );
        } else {
            showAlert("No Selection", "No Customer Selected", "Please select a customer in the table.");
        }
    }

    private void clearFields() {
        customerIdField.clear();
        customerNameField.clear();
        customerContactField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

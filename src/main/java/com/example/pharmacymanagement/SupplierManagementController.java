package com.example.pharmacymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import com.example.pharmacymanagement.Models.Supplier;

public class SupplierManagementController {

    @FXML
    private TextField supplierIdField;

    @FXML
    private TextField supplierNameField;

    @FXML
    private TextField supplierContactField;

    @FXML
    private TextField supplierAddressField;

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, String> supplierIdColumn;

    @FXML
    private TableColumn<Supplier, String> supplierNameColumn;

    @FXML
    private TableColumn<Supplier, String> supplierContactColumn;

    @FXML
    private TableColumn<Supplier, String> supplierAddressColumn;

    private ObservableList<Supplier> supplierData;

    public SupplierManagementController() {
        supplierData = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        supplierAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        supplierTable.setItems(supplierData);
    }

    @FXML
    private void handleAddSupplier(ActionEvent event) {
        Supplier supplier = new Supplier(supplierIdField.getText(), supplierNameField.getText(),
                supplierContactField.getText(), supplierAddressField.getText());
        supplierData.add(supplier);
        clearFields();
    }

    @FXML
    private void handleUpdateSupplier(ActionEvent event) {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            selectedSupplier.setSupplierId(supplierIdField.getText());
            selectedSupplier.setName(supplierNameField.getText());
            selectedSupplier.setContact(supplierContactField.getText());
            selectedSupplier.setAddress(supplierAddressField.getText());
            supplierTable.refresh();
            clearFields();
        } else {
            showAlert("No Selection", "No Supplier Selected", "Please select a supplier in the table.");
        }
    }

    @FXML
    private void handleDeleteSupplier(ActionEvent event) {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            supplierData.remove(selectedSupplier);
        } else {
            showAlert("No Selection", "No Supplier Selected", "Please select a supplier in the table.");
        }
    }

    private void clearFields() {
        supplierIdField.clear();
        supplierNameField.clear();
        supplierContactField.clear();
        supplierAddressField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

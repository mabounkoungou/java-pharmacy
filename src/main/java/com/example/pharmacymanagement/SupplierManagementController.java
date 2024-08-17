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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private Connection connection;

    public SupplierManagementController() {
        supplierData = FXCollections.observableArrayList();
        initializeDatabase();
    }

    private void initializeDatabase() {
        String url = "jdbc:mysql://localhost:3306/java";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error connecting to the database", e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        supplierAddressColumn.setCellValueFactory(new PropertyValueFactory<>("location")); // Note the mismatch in method name

        // Load data from database
        loadSupplierData();
        supplierTable.setItems(supplierData);
    }

    private void loadSupplierData() {
        try {
            String query = "SELECT * FROM suppliers";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String supplierId = resultSet.getString("supplierId");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                String location = resultSet.getString("location");

                Supplier supplier = new Supplier(supplierId, name, location, contact);
                supplierData.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error loading suppliers from database", e.getMessage());
        }
    }

    @FXML
    private void handleAddSupplier(ActionEvent event) {
        String supplierId = supplierIdField.getText();
        String name = supplierNameField.getText();
        String contact = supplierContactField.getText();
        String location = supplierAddressField.getText();

        Supplier newSupplier = new Supplier(supplierId, name, location, contact);
        saveSupplierToDatabase(newSupplier);
        supplierData.add(newSupplier);
        clearFields();
    }

    @FXML
    private void handleUpdateSupplier(ActionEvent event) {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            selectedSupplier.setSupplierId(supplierIdField.getText());
            selectedSupplier.setName(supplierNameField.getText());
            selectedSupplier.setContact(supplierContactField.getText());
            selectedSupplier.setLocation(supplierAddressField.getText());
            updateSupplierInDatabase(selectedSupplier);
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
            deleteSupplierFromDatabase(selectedSupplier);
            supplierData.remove(selectedSupplier);
        } else {
            showAlert("No Selection", "No Supplier Selected", "Please select a supplier in the table.");
        }
    }

    private void saveSupplierToDatabase(Supplier supplier) {
        try {
            String query = "INSERT INTO suppliers (supplierId, name, contact, location) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplier.getSupplierId());
            preparedStatement.setString(2, supplier.getName());
            preparedStatement.setString(3, supplier.getContact());
            preparedStatement.setString(4, supplier.getLocation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error saving supplier to database", e.getMessage());
        }
    }

    private void updateSupplierInDatabase(Supplier supplier) {
        try {
            String query = "UPDATE suppliers SET name = ?, contact = ?, location = ? WHERE supplierId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getContact());
            preparedStatement.setString(3, supplier.getLocation());
            preparedStatement.setString(4, supplier.getSupplierId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error updating supplier in database", e.getMessage());
        }
    }

    private void deleteSupplierFromDatabase(Supplier supplier) {
        try {
            String query = "DELETE FROM suppliers WHERE supplierId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplier.getSupplierId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error deleting supplier from database", e.getMessage());
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

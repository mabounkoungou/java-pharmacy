package com.example.pharmacymanagement;

import com.example.pharmacymanagement.Models.Purchase;
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

public class PurchaseController {

    @FXML
    private TextField drugIdField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField totalAmountField;

    @FXML
    private TextField purchaseDateField;

    @FXML
    private TableView<Purchase> purchaseTable;

    @FXML
    private TableColumn<Purchase, String> drugIdColumn;

    @FXML
    private TableColumn<Purchase, Integer> quantityColumn;

    @FXML
    private TableColumn<Purchase, Double> totalAmountColumn;

    @FXML
    private TableColumn<Purchase, String> purchaseDateColumn;

    private ObservableList<Purchase> purchaseData;

    private Connection connection;

    public PurchaseController() {
        purchaseData = FXCollections.observableArrayList();
        initializeDatabase();
    }

    private void initializeDatabase() {
        String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5718540";
        String user = "sql5718540";
        String password = "FmrdIXMIGJ";

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
        drugIdColumn.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));

        // Load data from database
        loadPurchaseData();
        purchaseTable.setItems(purchaseData);
    }

    private void loadPurchaseData() {
        try {
            String query = "SELECT * FROM purchases";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String drugId = resultSet.getString("drugId");
                int quantity = resultSet.getInt("quantity");
                double totalAmount = resultSet.getDouble("totalAmount");
                String purchaseDate = resultSet.getString("purchaseDate");

                Purchase purchase = new Purchase(drugId, quantity, totalAmount, purchaseDate);
                purchaseData.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error loading purchases from database", e.getMessage());
        }
    }

    @FXML
    private void handleAddPurchase(ActionEvent event) {
        String drugId = drugIdField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double totalAmount = Double.parseDouble(totalAmountField.getText());
        String purchaseDate = purchaseDateField.getText();

        Purchase newPurchase = new Purchase(drugId, quantity, totalAmount, purchaseDate);
        savePurchaseToDatabase(newPurchase);
        purchaseData.add(newPurchase);
        clearFields();
    }

    @FXML
    private void handleUpdatePurchase(ActionEvent event) {
        Purchase selectedPurchase = purchaseTable.getSelectionModel().getSelectedItem();
        if (selectedPurchase != null) {
            selectedPurchase.setDrugId(drugIdField.getText());
            selectedPurchase.setQuantity(Integer.parseInt(quantityField.getText()));
            selectedPurchase.setTotalAmount(Double.parseDouble(totalAmountField.getText()));
            selectedPurchase.setPurchaseDate(purchaseDateField.getText());
            updatePurchaseInDatabase(selectedPurchase);
            purchaseTable.refresh();
            clearFields();
        } else {
            showAlert("No Selection", "No Purchase Selected", "Please select a purchase in the table.");
        }
    }

    @FXML
    private void handleDeletePurchase(ActionEvent event) {
        Purchase selectedPurchase = purchaseTable.getSelectionModel().getSelectedItem();
        if (selectedPurchase != null) {
            deletePurchaseFromDatabase(selectedPurchase);
            purchaseData.remove(selectedPurchase);
        } else {
            showAlert("No Selection", "No Purchase Selected", "Please select a purchase in the table.");
        }
    }

    private void savePurchaseToDatabase(Purchase purchase) {
        try {
            String query = "INSERT INTO purchases (drugId, quantity, totalAmount, purchaseDate) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, purchase.getDrugId());
            preparedStatement.setInt(2, purchase.getQuantity());
            preparedStatement.setDouble(3, purchase.getTotalAmount());
            preparedStatement.setString(4, purchase.getPurchaseDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error saving purchase to database", e.getMessage());
        }
    }

    private void updatePurchaseInDatabase(Purchase purchase) {
        try {
            String query = "UPDATE purchases SET quantity = ?, totalAmount = ?, purchaseDate = ? WHERE drugId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, purchase.getQuantity());
            preparedStatement.setDouble(2, purchase.getTotalAmount());
            preparedStatement.setString(3, purchase.getPurchaseDate());
            preparedStatement.setString(4, purchase.getDrugId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error updating purchase in database", e.getMessage());
        }
    }

    private void deletePurchaseFromDatabase(Purchase purchase) {
        try {
            String query = "DELETE FROM purchases WHERE drugId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, purchase.getDrugId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error deleting purchase from database", e.getMessage());
        }
    }

    private void clearFields() {
        drugIdField.clear();
        quantityField.clear();
        totalAmountField.clear();
        purchaseDateField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

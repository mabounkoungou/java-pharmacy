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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.pharmacymanagement.Models.Drug;

public class DrugManagementController {

    @FXML
    private TextField drugIdField;

    @FXML
    private TextField drugNameField;

    @FXML
    private TextField drugCategoryField;

    @FXML
    private TextField drugPriceField;

    @FXML
    private TextField drugQuantityField;

    @FXML
    private TableView<Drug> drugTable;

    @FXML
    private TableColumn<Drug, String> drugIdColumn;

    @FXML
    private TableColumn<Drug, String> drugNameColumn;

    @FXML
    private TableColumn<Drug, String> drugCategoryColumn;

    @FXML
    private TableColumn<Drug, Double> drugPriceColumn;

    @FXML
    private TableColumn<Drug, Integer> drugQuantityColumn;

    private ObservableList<Drug> drugData;

    private Connection connection;

    public DrugManagementController() {
        drugData = FXCollections.observableArrayList();
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
        drugNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        drugCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        drugPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        drugQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Load data from database
        loadDrugData();
        drugTable.setItems(drugData);
    }

    private void loadDrugData() {
        try {
            String query = "SELECT * FROM drugs";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String drugId = resultSet.getString("drugId");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Drug drug = new Drug(drugId, name, category, price, quantity);
                drugData.add(drug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error loading drugs from database", e.getMessage());
        }
    }

    @FXML
    private void handleAddDrug(ActionEvent event) {
        String drugId = drugIdField.getText();
        String name = drugNameField.getText();
        String category = drugCategoryField.getText();
        double price = Double.parseDouble(drugPriceField.getText());
        int quantity = Integer.parseInt(drugQuantityField.getText());

        Drug newDrug = new Drug(drugId, name, category, price, quantity);
        saveDrugToDatabase(newDrug);
        drugData.add(newDrug);
        clearFields();
    }

    @FXML
    private void handleUpdateDrug(ActionEvent event) {
        Drug selectedDrug = drugTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            selectedDrug.setDrugId(drugIdField.getText());
            selectedDrug.setName(drugNameField.getText());
            selectedDrug.setCategory(drugCategoryField.getText());
            selectedDrug.setPrice(Double.parseDouble(drugPriceField.getText()));
            selectedDrug.setQuantity(Integer.parseInt(drugQuantityField.getText()));
            updateDrugInDatabase(selectedDrug);
            drugTable.refresh();
            clearFields();
        } else {
            showAlert("No Selection", "No Drug Selected", "Please select a drug in the table.");
        }
    }

    @FXML
    private void handleDeleteDrug(ActionEvent event) {
        Drug selectedDrug = drugTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            deleteDrugFromDatabase(selectedDrug);
            drugData.remove(selectedDrug);
        } else {
            showAlert("No Selection", "No Drug Selected", "Please select a drug in the table.");
        }
    }

    private void saveDrugToDatabase(Drug drug) {
        try {
            String query = "INSERT INTO drugs (drugId, name, category, price, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, drug.getDrugId());
            preparedStatement.setString(2, drug.getName());
            preparedStatement.setString(3, drug.getCategory());
            preparedStatement.setDouble(4, drug.getPrice());
            preparedStatement.setInt(5, drug.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error saving drug to database", e.getMessage());
        }
    }

    private void updateDrugInDatabase(Drug drug) {
        try {
            String query = "UPDATE drugs SET name = ?, category = ?, price = ?, quantity = ? WHERE drugId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, drug.getName());
            preparedStatement.setString(2, drug.getCategory());
            preparedStatement.setDouble(3, drug.getPrice());
            preparedStatement.setInt(4, drug.getQuantity());
            preparedStatement.setString(5, drug.getDrugId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error updating drug in database", e.getMessage());
        }
    }

    private void deleteDrugFromDatabase(Drug drug) {
        try {
            String query = "DELETE FROM drugs WHERE drugId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, drug.getDrugId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error deleting drug from database", e.getMessage());
        }
    }

    private void clearFields() {
        drugIdField.clear();
        drugNameField.clear();
        drugCategoryField.clear();
        drugPriceField.clear();
        drugQuantityField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

package com.example.pharmacymanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private void handleManageDrugs() {
        loadUI("/com/example/pharmacymanagement/DrugManagement.fxml");
    }

    @FXML
    private void handleManageSuppliers() {
        loadUI("/com/example/pharmacymanagement/SupplierManagement.fxml");
    }

    @FXML
    private void handleProcessSales() {
        loadUI("/com/example/pharmacymanagement/SalesManagement.fxml");
    }

    @FXML
    private void handleViewReports() {
        loadUI("/com/example/pharmacymanagement/ReportView.fxml");
    }

    @FXML
    private void handleLogout() {
        showAlert("Logout", "You have been logged out.");
        // Implement logout logic here
    }

    private void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(root); // Replace the center of the BorderPane
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

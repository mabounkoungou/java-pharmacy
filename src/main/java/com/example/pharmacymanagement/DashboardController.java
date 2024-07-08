package com.example.pharmacymanagement;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button btnManageDrugs;

    @FXML
    private Button btnManageSuppliers;

    @FXML
    private Button btnProcessSales;

    @FXML
    private Button btnViewReports;

    @FXML
    private Button btnLogout;

    @FXML
    private void handleManageDrugs() {
        loadUI("/com/example/pharmacymanagement/DrugManagement.fxml");
        flashButton(btnManageDrugs);
    }

    @FXML
    private void handleManageSuppliers() {
        loadUI("/com/example/pharmacymanagement/SupplierManagement.fxml");
        flashButton(btnManageSuppliers);
    }

    @FXML
    private void handleProcessSales() {
        loadUI("/com/example/pharmacymanagement/SalesManagement.fxml");
        flashButton(btnProcessSales);
    }

    @FXML
    private void handleViewReports() {
        loadUI("/com/example/pharmacymanagement/ReportView.fxml");
        flashButton(btnViewReports);
    }

    @FXML
    private void handleLogout() {
        showAlert("Logout", "You have been logged out.");
        closeCurrentWindow(); // Optionally close the current window
        loadLoginScreen();    // Load the login screen
    }

    private void closeCurrentWindow() {
        // Close the current window (optional)
        mainPane.getScene().getWindow().hide();
    }

    private void loadLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/Login.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login window
            mainPane.getScene().getWindow().hide(); // Hide the current window
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void flashButton(Button button) {
        // Change the button's style temporarily
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(button.styleProperty(), "-fx-base: #4CAF50; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius:20; -fx-background-radius:20;")),
                new KeyFrame(Duration.millis(300), new KeyValue(button.styleProperty(), "-fx-base: #2E8B57; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius:20; -fx-background-radius:20;"))
        );
        timeline.play();
    }
}

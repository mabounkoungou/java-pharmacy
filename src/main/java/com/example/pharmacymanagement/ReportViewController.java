package com.example.pharmacymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import com.example.pharmacymanagement.Models.Report;

public class ReportViewController {

    @FXML
    private TableView<Report> reportTable;

    @FXML
    private TableColumn<Report, String> reportIdColumn;

    @FXML
    private TableColumn<Report, String> reportNameColumn;

    @FXML
    private TableColumn<Report, String> reportDateColumn;

    @FXML
    private TableColumn<Report, String> reportDetailsColumn;

    private ObservableList<Report> reportData;

    public ReportViewController() {
        reportData = FXCollections.observableArrayList();
        // Add sample data to the reportData list
        reportData.add(new Report("1", "Monthly Sales", "2024-06-23", "Details about monthly sales..."));
        reportData.add(new Report("2", "Supplier Report", "2024-06-22", "Details about suppliers..."));
        reportData.add(new Report("3", "Customer Report", "2024-06-21", "Details about customers..."));
    }

    @FXML
    private void initialize() {
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        reportNameColumn.setCellValueFactory(new PropertyValueFactory<>("reportName"));
        reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        reportDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("reportDetails"));

        reportTable.setItems(reportData);
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/Dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) reportTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

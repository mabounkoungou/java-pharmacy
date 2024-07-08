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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.pharmacymanagement.Models.Report;

public class ReportViewController {

    @FXML
    private TableView<Report> reportTable;

    @FXML
    private TableColumn<Report, String> reportIdColumn;

    @FXML
    private TableColumn<Report, String> reportNameColumn;

    @FXML
    private TableColumn<Report, LocalDate> reportDateColumn;

    @FXML
    private TableColumn<Report, String> reportDetailsColumn;

    private ObservableList<Report> reportData;

    private Connection connection;

    public ReportViewController() {
        reportData = FXCollections.observableArrayList();
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
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        reportNameColumn.setCellValueFactory(new PropertyValueFactory<>("reportName"));
        reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        reportDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("reportDetails"));

        // Load data from database
        loadReportData();
        reportTable.setItems(reportData);
    }

    private void loadReportData() {
        try {
            String query = "SELECT * FROM reports";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String reportId = resultSet.getString("reportId");
                String reportName = resultSet.getString("reportName");
                LocalDate reportDate = resultSet.getDate("reportDate").toLocalDate();
                String reportDetails = resultSet.getString("reportDetails");

                Report report = new Report(reportId, reportName, reportDate, reportDetails);
                reportData.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error loading reports from database", e.getMessage());
        }
    }

    // Method to handle purchases
    public void handlePurchase(String drugId, int quantityPurchased, double totalAmount) {
        try {
            // Update drug quantity in the database
            String updateQuery = "UPDATE drugs SET quantity = quantity - ? WHERE drugId = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, quantityPurchased);
            updateStatement.setString(2, drugId);
            updateStatement.executeUpdate();

            // Insert daily report into database
            generateDailyReport(totalAmount);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error handling purchase", e.getMessage());
        }
    }

    private void generateDailyReport(double totalAmount) {
        try {
            LocalDate currentDate = LocalDate.now();
            String reportName = "Daily Purchase Report";
            String reportDetails = "Total Purchases: $" + totalAmount;
            String insertQuery = "INSERT INTO reports (reportName, reportDate, reportDetails) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, reportName);
            insertStatement.setString(2, currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            insertStatement.setString(3, reportDetails);
            insertStatement.executeUpdate();

            // Refresh report data to update the table
            reportData.clear();
            loadReportData();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error generating daily report", e.getMessage());
        }
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
            showAlert("Navigation Error", "Error navigating to dashboard", e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

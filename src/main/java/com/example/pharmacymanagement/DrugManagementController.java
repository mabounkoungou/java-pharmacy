package com.example.pharmacymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

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

    public DrugManagementController() {
        drugData = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        drugIdColumn.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        drugNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        drugCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        drugPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        drugQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        drugTable.setItems(drugData);
    }

    @FXML
    private void handleAddDrug(ActionEvent event) {
        Drug drug = new Drug(drugIdField.getText(), drugNameField.getText(), drugCategoryField.getText(),
                Double.parseDouble(drugPriceField.getText()), Integer.parseInt(drugQuantityField.getText()));
        drugData.add(drug);
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
            drugData.remove(selectedDrug);
        } else {
            showAlert("No Selection", "No Drug Selected", "Please select a drug in the table.");
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

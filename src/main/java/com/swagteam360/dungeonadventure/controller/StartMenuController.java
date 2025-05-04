package com.swagteam360.dungeonadventure.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {

    @FXML
    private void startButtonEvent(ActionEvent theActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void quitButtonEvent(ActionEvent theActionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Click OK to exit.");

        javafx.scene.control.ButtonType result = alert.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);

        if (result == javafx.scene.control.ButtonType.OK) {
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void newGameEvent(ActionEvent theActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/swagteam360/dungeonadventure/game-customization.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonToStartScreenEvent(ActionEvent theActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/swagteam360/dungeonadventure/start-menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonToSecondaryMenuEvent(ActionEvent theActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) theActionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void aboutUsMenuEvent(ActionEvent theActionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Us");
        alert.setHeaderText("Dungeon Adventure");
        alert.setContentText("Created by Preston Sia, Jonathan Hernandez, and Luke Willis " +
                "for course project for TCSS360 Spring 2025.\n\nVersion 1.0\n\nEnjoy your quest.");
        alert.showAndWait();

    }

}

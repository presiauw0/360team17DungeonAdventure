package com.swagteam360.dungeonadventure.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenu extends Application implements Initializable {

    private static Stage myPrimaryStage;

    public static void main(String[] theArgs) {
        launch(theArgs);
    }

    @Override
    public void start(Stage thePrimaryStage) throws Exception {

        try {

            myPrimaryStage = thePrimaryStage;

            Parent root = FXMLLoader.load(getClass().getResource("/com/swagteam360/dungeonadventure/start-menu.fxml"));
            Scene scene = new Scene(root, 600, 400);

            myPrimaryStage.setScene(scene);
            myPrimaryStage.setResizable(false);
            myPrimaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void startButtonEvent(ActionEvent theActionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/swagteam360/dungeonadventure/secondary-menu.fxml"));
            Parent secondaryRoot = loader.load();
            Scene secondaryScene = new Scene(secondaryRoot, 600, 400);
            myPrimaryStage.setScene(secondaryScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

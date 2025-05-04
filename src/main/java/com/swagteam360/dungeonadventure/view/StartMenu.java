package com.swagteam360.dungeonadventure.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartMenu extends Application {

    public static void main(String[] theArgs) {
        launch(theArgs);
    }

    @Override
    public void start(Stage thePrimaryStage) throws Exception {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/com/swagteam360/dungeonadventure/start-menu.fxml"));
            Scene scene = new Scene(root, 600, 400);

            thePrimaryStage.setScene(scene);
            thePrimaryStage.setResizable(false);
            thePrimaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

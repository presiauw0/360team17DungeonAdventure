module com.swagteam360.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.swagteam360.dungeonadventure to javafx.fxml;
    exports com.swagteam360.dungeonadventure;

    opens com.swagteam360.dungeonadventure.view to javafx.fxml;
    opens com.swagteam360.dungeonadventure.controller to javafx.fxml;

    exports com.swagteam360.dungeonadventure.view;
    exports com.swagteam360.dungeonadventure.controller;


}
module com.swagteam360.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.swagteam360.dungeonadventure to javafx.fxml;
    exports com.swagteam360.dungeonadventure;
}
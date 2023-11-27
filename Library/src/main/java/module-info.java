module project.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens project.finalproject to javafx.fxml;
    exports project.finalproject;
}
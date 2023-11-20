module project.finalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.finalproject to javafx.fxml;
    exports project.finalproject;
}
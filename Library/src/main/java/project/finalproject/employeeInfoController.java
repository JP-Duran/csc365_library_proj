package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class employeeInfoController {

    public Button BackButton;

    @FXML
    void onBackBttn(ActionEvent event)throws Exception {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("employeeInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();
    }
}

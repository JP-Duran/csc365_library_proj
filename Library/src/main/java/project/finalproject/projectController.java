package project.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class projectController {
    public Button employeeButton;
    public Button userButton;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onUser()throws Exception {
        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();
        //open the user login window. moves control to userInterfaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("userLoginWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        newStage.setTitle("User Login");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    protected void onEmployee()throws Exception{
        Stage stage = (Stage) employeeButton.getScene().getWindow();
        stage.close();
        //open the employee login window. moves control to employeeInterfaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("front.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Employee Login");
        newStage.setScene(scene);
        newStage.show();

    }
}
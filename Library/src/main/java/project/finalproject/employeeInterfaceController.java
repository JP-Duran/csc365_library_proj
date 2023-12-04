package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static project.finalproject.projectApplication.connect;

public class employeeInterfaceController {

    public Button BackButton;
    public Button infoButton;
    public Button SearchButton;
    public Button bookButton;



    @FXML
    void onBackBttn(ActionEvent event)throws Exception {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("front.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();
    }
    @FXML
    void addBooks(ActionEvent event)throws Exception {
        Stage stage = (Stage) bookButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("addbook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Employee Screen!");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void onSearchBooks(ActionEvent event) throws Exception {
        Stage stage = (Stage) SearchButton.getScene().getWindow();
        stage.close();

        //else open the user interface. controller: userInterFaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("searchbook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Search Window");
        newStage.setScene(scene);
        newStage.show();

    }

    @FXML
    void getInfo (ActionEvent event)throws Exception {
        Stage stage = (Stage) infoButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        //add info here
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("employeeInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Employee Screen!");
        newStage.setScene(scene);
        newStage.show();
    }


}

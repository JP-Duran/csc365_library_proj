package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class searchBookController {

    public Button BackButton;
    public Button SearchButton;
    @FXML
    private TextField genre;

    @FXML
    private TextField isbn;

    @FXML
    private TextField libID;

    @FXML
    private TextField title;

    @FXML
    void onBackBttn(ActionEvent event) throws Exception {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("employeeInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void onSearchBook(ActionEvent event) throws Exception {
        Stage stage = (Stage) SearchButton.getScene().getWindow();
        stage.close();
        //get the userName and password entered
        String bTitle = title.getText().strip();
        String bISBN = isbn.getText().strip();
        String bGenre = genre.getText().strip();
        String bLibID = libID.getText().strip();

        //Look up book from database

        //else open the user interface. controller: userInterFaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("searchbook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Search Window");
        newStage.setScene(scene);
        newStage.show();

    }
}

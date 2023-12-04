package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

/*
opened through the projectController
 */
public class userLoginWindowController {

    public Button loginButton;
    public Button backButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label notifyTxt;
    @FXML
    private TextField usernameTxt;

    @FXML
    void onBackBttn(ActionEvent event)throws Exception {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("hello-view.fxml"));
        //userInterfaceController controller = fxmlLoader.getController();
        //controller.getData("Library Card Number: ", "User Password");
        Scene scene = new Scene(fxmlLoader.load());
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void onLoginBttn(ActionEvent event)throws Exception {
        //get the userName and password entered
        String userName = usernameTxt.getText().strip();
        String userPassword = passwordField.getText().strip();
        //if invalid login information is given, display this txt saying invalid login information was given
        if(!true)
        {
            notifyTxt.setText("Invalid library card number or password entered");
        }
        //if valid login information given open the user interface
        else
        {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("userInterface.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            //get the controller to pass data to it for the next window/scene
            userInterfaceController controller = fxmlLoader.getController();
            //function in interFaceController that will receive the data
            controller.getData(userName, userPassword);
            newStage.setTitle("User Window");
            newStage.setScene(scene);
            newStage.show();
        }


    }

}
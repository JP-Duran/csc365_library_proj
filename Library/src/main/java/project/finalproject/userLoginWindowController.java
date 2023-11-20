package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField usernameTxt;

    @FXML
    void onBackBttn(ActionEvent event)throws Exception {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 687, 182);
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void onLoginBttn(ActionEvent event)throws Exception {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        //get the userName and password entered
        String userName = usernameTxt.getText().strip();
        String userPassword = passwordField.getText().strip();

        //if the library card or password in invalid display this
        if(false)
        {
            Notifications alert = new Notifications();
            alert.alertOk("Invalid", "And invalid library card number or password was entered");
        }

        //else open the user interface. controller: userInterFaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("userInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("User Window");
        newStage.setScene(scene);
        newStage.show();

    }

}
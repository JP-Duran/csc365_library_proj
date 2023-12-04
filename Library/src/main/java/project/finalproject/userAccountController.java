/*
should be opened through the userInterFaceController
when the user wants to see their account information or reset their password
 */
package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class userAccountController {

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane backGroundAnchor;

    @FXML
    private PasswordField currPassTxt;

    @FXML
    private Label infoLbl;

    @FXML
    private TextField libCardNumTxt;

    @FXML
    private Button resetButton;

    @FXML
    private PasswordField resetPassTxt;
    @FXML
    private String libCardNum = "";
    private String password = "";
    public void getData(String libCardNum, String password){
        this.libCardNum = libCardNum;
        this.password = password;
    }
    @FXML
    void onBackAction(ActionEvent event) {
        try {
            Stage stage = (Stage) backGroundAnchor.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("userInterFace.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            userInterfaceController controller = fxmlLoader.getController();
            controller.getData(libCardNum,password);
            newStage.setTitle("Welcome");
            newStage.setScene(scene);
            newStage.show();
        }
        catch (java.io.IOException e)
        {
            System.out.println("something went wrong");
        }
    }

    @FXML
    void onResetBttn(ActionEvent event) {
        if (!(libCardNumTxt.getText().strip()).equals(libCardNum) || !(currPassTxt.getText().strip()).equals(password)){
            infoLbl.setTextFill(Color.RED);
            infoLbl.setText("Enter the correct library card number and password to reset");
        }
        else {
            infoLbl.setTextFill(Color.GREEN);
            infoLbl.setText("Password has been reset successfully");
            String oldPass = currPassTxt.getText().strip();
            password = resetPassTxt.getText().strip();
            //sql to update the database
            /*
            enter sql query to update the user's password
             */
        }
    }

}

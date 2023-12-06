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

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static project.finalproject.projectApplication.connect;

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
        //user_login(libCardNumTxt.getText().strip(),currPassTxt.getText().strip());
        if (!user_login(libCardNumTxt.getText().strip(),currPassTxt.getText().strip()) ||
                !libCardNumTxt.getText().equals(libCardNum)){
            infoLbl.setTextFill(Color.RED);
            infoLbl.setText("Enter the correct library card number and password to reset");
        }
        else {
            infoLbl.setTextFill(Color.GREEN);
            infoLbl.setText("Password has been reset successfully");
            String oldPass = currPassTxt.getText().strip();
            password = resetPassTxt.getText().strip();
            update_user_password(libCardNum,password);
        }
    }

    /*
     * update user password
     * PARAMS: cardNum, newPassword
     * RETURN: true if update successful, false otherwise
     */
    public boolean update_user_password(String card_number, String new_pw) {
        try {
            String update_password_query = "update Users set upassword = ? where cardNum = ?";
            PreparedStatement prepared_query = connect.prepareStatement(update_password_query);
            prepared_query.setString(1, new_pw);
            prepared_query.setString(2, card_number);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /* Queries the Users table for a matching username password combo
     * PARAMS: user_id - the username to query
     *         user_pw - the password to query
     * RETURN: true - if user 'user_id' exists and the password 'user_pw' matches their password
     *         false - if the user does not exist or the password does not match
     */
    public boolean user_login(String libCardNum, String user_pw) {
        try {
            ResultSet result;
            String user_id_query = "select Users.upassword from Users where cardNum = ?";
            PreparedStatement prepared_query = connect.prepareStatement(user_id_query);
            prepared_query.setString(1, libCardNum);
            result = prepared_query.executeQuery();
            // This will throw exception if user does not exist
            result.next();
            // System.out.println(result.getString("upassword"));
            if (user_pw.equals(result.getString("upassword"))) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

}

package project.finalproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import static project.finalproject.projectApplication.connect;

/*
opened through the projectController
 */
public class employeeLoginWindowController {

    public Button BackButton;
    public Button loginButton;
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTxt;
    @FXML
    private String userName = "";
    private String password = "";
    public void getData(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @FXML
    void onBackBttn(ActionEvent event)throws Exception {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 687, 182);
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();
    }

    public boolean employee_login(String emp_id, String emp_pw) {
        try {
            String emp_id_query = "select Employees.emppassword from Employees where empid = ?";
            PreparedStatement prepared_query = connect.prepareStatement(emp_id_query);
            prepared_query.setString(1, emp_id);
            ResultSet result = prepared_query.executeQuery();
            // This will throw exception if employee does not exist
            result.next();
            System.out.println(emp_pw.equals(result.getString("emppassword")));
            if (emp_pw.equals(result.getString("emppassword"))) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    @FXML
    void onLoginBttn(ActionEvent event)throws Exception {
        //get the userName and password entered
        userName = usernameTxt.getText().strip();
        password = passwordField.getText().strip();

        //if the library card or password in invalid display this
        if(employee_login(userName, password))
        {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("employeeInterface.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);
            newStage.setTitle("Employee Window");
            newStage.setScene(scene);
            newStage.show();
        }
        else{
            Notifications alert = new Notifications();
            alert.alertOk("Invalid", "And invalid library card number or password was entered");
        }
    }

}
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

public class employeeInfoController {

    public Button BackButton;
    public Label pw;
    public Label id;
    public Label loc;

    public ArrayList<String> personal_info_query(String empid) {
        ArrayList<String> info = new ArrayList<>();
        try {
            String info_query = "select Libraries.llocation, Employees.emppassword from Employees join Libraries on Employees.lid = Libraries.lid where Employees.empid = ?";
            PreparedStatement prepared_query = connect.prepareStatement(info_query);
            prepared_query.setString(1, empid);
            ResultSet result = prepared_query.executeQuery();
            if (!result.next()) { return null; }
            do {
                info.add(result.getString("llocation"));
                info.add(result.getString("emppassword"));
            } while (result.next());
        } catch (Exception e) { /*Ignore */ }
        return info;
    }
    @FXML
    public void initialize(){
        String user = employeeLoginWindowController.getUser();
        ArrayList<String> r = personal_info_query(user);
        id.setText(user);
        loc.setText(r.get(0));
        pw.setText(r.get(1));
    }
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

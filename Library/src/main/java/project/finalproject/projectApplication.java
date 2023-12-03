package project.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
public class projectApplication extends Application {
    /*
    -we might have to make multiple fxml files for each window we want to make
    -new Stage() sets the new window
    -new Scene() sets the content for that window
     */
    public static Connection connect;
    public ResultSet result;
    public final String jdbc_link = "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/thegoats?user=thegoats&password=someSecurePassword";
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 687, 182);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }
    public void init_connection() {
}
    public static void main(String[] args) {

        /* Initializes a connection to the SQL server
         * +++ Connection must be closed when finished +++
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/thegoats?user=thegoats&password=someSecurePassword");
            String emp_id_query = "select * from Employees";
            PreparedStatement prepared_query = connect.prepareStatement(emp_id_query);
            ResultSet result = prepared_query.executeQuery();
            // This will throw exception if employee does not exist
            result.next();
            System.out.println(result.getString("empid"));
            System.out.println(result.getString("emppassword"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch();
    }
}
package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

import static project.finalproject.projectApplication.connect;

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

    public boolean remove_book(String isbn) {
        try {
            String remove_book_query = "delete from Books where isbn = ?";
            PreparedStatement prepared_query = connect.prepareStatement(remove_book_query);
            prepared_query.setString(1, isbn);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    @FXML
    void onSearchBook(ActionEvent event) throws Exception {
        Stage stage = (Stage) SearchButton.getScene().getWindow();
        stage.close();
        //get the userName and password entered
        String bISBN = isbn.getText().strip();
        //Look up book from database
        if(remove_book(bISBN))
        {
            Notifications alert = new Notifications();
            alert.alertOk("Remove Book", "Book with ISBN:" + bISBN + " has been removed.");
        }
        else{
            Notifications alert = new Notifications();
            alert.alertOk("Error", "Something went wrong!");
        }
        //else open the user interface. controller: userInterFaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("searchbook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Remove Window");
        newStage.setScene(scene);
        newStage.show();

    }
}

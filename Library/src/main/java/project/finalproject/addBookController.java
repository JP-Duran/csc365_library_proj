package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

import static project.finalproject.projectApplication.connect;

public class addBookController {

    public Button BackButton;
    public Button addButton;
    @FXML
    private TextField genre;

    @FXML
    private TextField isbn;

    @FXML
    private TextField libID;

    @FXML
    private TextField title;
    ArrayList<String> validGenres = new ArrayList<>(Arrays.asList("fiction", "nonfiction", "mystery", "romance", "fantasy"));
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
    public boolean add_book(String genre, String book_title, String isbn, Integer lid) {
        try {
            genre = genre.toLowerCase();
            if (!validGenres.contains(genre) || book_title == null || isbn == null || lid == null) {
                return false;
            }
            String add_book_query = "insert into Books (genre, bname, isbn, lid, available) values (?, ?, ?, ?, ?)";
            PreparedStatement prepared_query = connect.prepareStatement(add_book_query);
            prepared_query.setString(1, genre);
            prepared_query.setString(2, book_title);
            prepared_query.setString(3, isbn);
            prepared_query.setInt(4, lid);
            prepared_query.setInt(5, 1);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }
    @FXML
    void insertBook (ActionEvent event)throws Exception {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        //get the userName and password entered
        String bTitle = title.getText().strip();
        String bISBN = isbn.getText().strip();
        String bGenre = genre.getText().strip();
        String bLibID = libID.getText().strip();
        if(add_book(bGenre, bTitle, bISBN, Integer.valueOf(bLibID)))
        {
            Notifications alert = new Notifications();
            alert.alertOk("New Book", "A new book has been added!");
        }
        else{
            Notifications alert = new Notifications();
            alert.alertOk("Error", "Something went wrong!");
        }


        //else open the user interface. controller: userInterFaceController
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("addbook.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        newStage.setTitle("Add Window");
        newStage.setScene(scene);
        newStage.show();

    }


}

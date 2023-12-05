package project.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Vector;

import static project.finalproject.projectApplication.connect;

public class userInterfaceController implements Initializable {
    /*
    Idea: use a hbox to display book information. set a bar set a limit on the amount of characters that can be displayed. If they exceed this char limit prune the last three chars and add "..." to the end of the string
        each hbox can be assigned one of two colors and have them alterante. set a variable (boolean?) to alterante as we iterate through sql variable.
    Idea: a vbox that has hbox's
     */
    @FXML
    private AnchorPane backAnchor;

    @FXML
    private TextField bkNameTxt;

    @FXML
    private ChoiceBox<String> dropDownChcBox;

    @FXML
    private ChoiceBox<String> genreChoice;

    @FXML
    private ChoiceBox<String> locationChoice;

    @FXML
    private BorderPane resultsBorderPane;

    @FXML
    private ToolBar userToolBar;
    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox resultVBox;
    @FXML
    private Label bkLbl;
    @FXML
    private Label llLabel;
    @FXML
    private Label gLbl;
    private String libCardNum = "";
    private String password = "";
    //strings that will be displayed in dropdown menu
    private String[] futureButtons = {"Account", "Logout"};
    private String[] genres = {"NONE","fiction", "nonfiction", "mystery", "romance", "fantasy"};
    private String[] locations = {"NONE","SLO","Arroyo Grande","Los Osos", "San Miguel"};
    public void getData(String libCardNum, String password){
        this.libCardNum = libCardNum;
        this.password = password;
    }

    @Override
    //this seems to run the moment the fxml file is loaded in from the userLoginWindowController
    //called behind the scene to initialize a controller once it's root element has been processed
    public void initialize(URL url, ResourceBundle resourceBundle){
        Stop[] stops = new Stop[] {new Stop(0, Color.DARKBLUE), new Stop(1, Color.DARKSEAGREEN)};
        LinearGradient lgcolor = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
        //first argument seems to be able to take different color types; you can substitute the 'Color.DARKSEAGREEN' argument with variable 'lgcolor' for the gradient
        BackgroundFill bgfill = new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        backAnchor.setBackground(new Background(bgfill));
        BackgroundFill toolBarFill = new BackgroundFill(lgcolor,CornerRadii.EMPTY, Insets.EMPTY);
        userToolBar.setBackground(new Background(toolBarFill));

        dropDownChcBox.setValue("Settings");
        dropDownChcBox.getItems().addAll(futureButtons);
        //'this::' is a method reference operator, reference to our method and link it to a node of dropDownChcBox
        dropDownChcBox.setOnAction(this::buttons);
        //for genre drop down menu
        genreChoice.setValue("Genre Options");
        genreChoice.getItems().addAll(genres);
        //add fucntion to it
        //for location drop down menu
        locationChoice.setValue("Location Options");
        locationChoice.getItems().addAll(locations);
        //add function to it

        //setting up the search portion up
        scrollPane = new ScrollPane();
        scrollPane.setBackground(new Background(bgfill));

        resultVBox = new VBox();
        resultVBox.setBackground(new Background(toolBarFill));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(false);
        scrollPane.setContent(resultVBox);
        resultsBorderPane.setCenter(scrollPane);
        scrollPane.setOpacity(0);
        gLbl.setOpacity(0);
        llLabel.setOpacity(0);
        bkLbl.setOpacity(0);
    }
    public void buttons(ActionEvent event){
        String button = dropDownChcBox.getValue();
        System.out.println(button);
        if (button.equals("Logout")){
            try {
                Stage stage = (Stage) backAnchor.getScene().getWindow();
                stage.close();
                Stage newStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                newStage.setTitle("Welcome");
                newStage.setScene(scene);
                newStage.show();

            }
            catch (java.io.IOException e)
            {
                System.out.println("something went wrong");
            }
        }
        else if (button.equals("Account")){
            try {
                Stage stage = (Stage) backAnchor.getScene().getWindow();
                stage.close();
                Stage newStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(projectApplication.class.getResource("userAccount.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                userAccountController controller = fxmlLoader.getController();
                controller.getData(libCardNum,password);
                newStage.setTitle("Account");
                newStage.setScene(scene);
                newStage.show();
            }
            catch (java.io.IOException e)
            {
                System.out.println("something went wrong");
            }
        }
    }
    @FXML
    void searchOnAction(ActionEvent event) {
        if (bkNameTxt.getText().isEmpty()  && genreChoice.getValue().equals("Genre Options") &&
                locationChoice.getValue().equals("Location Options"))
        {
            String result = Notifications.yesOrNo("No Selection", "You haven't entered anything to search books" +
                    " by. Press 'yes' if you would like to a list of all the books. Otherwise press 'no.'");
            if (result.equals("Yes")){
                showResults("","",null);
            }
        }
        else {
            String tempBKName = bkNameTxt.getText();
            String tempGenre;
            Integer libLocationID;
            if (genreChoice.getValue().equals("Genre Options") || genreChoice.getValue().equals("NONE")){
                tempGenre = "";
            }
            else {
                tempGenre = genreChoice.getValue();
            }
            if (locationChoice.getValue().equals("Location Options") || locationChoice.getValue().equals("NONE")){
                libLocationID = null;
            }
            else{
                libLocationID = get_library_id(locationChoice.getValue());
            }
            showResults(tempBKName,tempGenre,libLocationID);
        }

        /*
        if (!genreChoice.getValue().equals("NONE"))
        {
            System.out.println(genreChoice.getValue());
            System.out.println(locationChoice.getValue());
            System.out.println(bkNameTxt.getText());
        }
        if (bkNameTxt.getText().isEmpty()){
            //System.out.println("show this");
        }
        BackgroundFill bgfill = new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        Button button2 = new Button("Checkout");
        button2.setId("store Isbn number here");
        button2.setOnMouseClicked(this::buttonsAllClick);
        //Button[] addButtons = {new Button("three")}
        HBox hBox = new HBox(new TextField("test"),button2);
        Button button1 = new Button("Checkout");
        button1.setOnMouseClicked(this::buttonsAllClick);
        hBox.setPrefHeight(HBOXHEIGHT);
        hBox.setPrefWidth(BOXWIDTH);

        HBox hBox1 = new HBox(new TextField("test 2"),button1);
        //use .setID() to store the isbn number?
        hBox1.setBackground(new Background(bgfill));
        hBox.setStyle("-fx-background-color: A9D3FF");
        resultVBox.getChildren().addAll(hBox,hBox1);

         */
    }
    private void showResults(String bookName, String genre, Integer libLocation){
        //width - 15 if exceeds space; width - 2 if it doesn't
        //302 is the max horizontal height before a scroll bar appears
        final double BOXWIDTHOVER = resultsBorderPane.getWidth() - 15;
        final double BOXWIDTH = resultsBorderPane.getWidth() - 2;
        final double HBOXHEIGHT = 25;
        final double PANEHEIGHT = 302;
        scrollPane.setOpacity(100);
        bkLbl.setOpacity(1000);
        gLbl.setOpacity(100);
        llLabel.setOpacity(100);
        boolean green = false;
        Notifications notifications = new Notifications();

        ResultSet resultSet = search_book(genre,bookName,"",libLocation);
        int bkcount = 0;
        try{
            resultVBox.getChildren().clear();
            while (resultSet.next()){
                bkcount++;
                BackgroundFill bgfill = new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY);
                HBox hBox = new HBox();
                Label bkName = new Label();
                Label genreName = new Label();
                Label libName = new Label();
                Button button = new Button("Checkout");
                button.setId(resultSet.getString("isbn"));
                button.setOnMouseClicked(this::buttonsAllClick);
                if (green) {
                    hBox.setBackground(new Background(bgfill));
                    green = false;
                }
                else {
                    hBox.setStyle("-fx-background-color: A9D3FF");
                    green = true;
                }

                bkName.setText(resultSet.getString("bname"));
                genreName.setText(resultSet.getString("genre"));
                libName.setText(get_library_name(Integer.parseInt(resultSet.getString("lid"))));
                bkName.setStyle("-fx-font-family: Monospaced");
                genreName.setStyle("-fx-font-family: Monospaced");
                libName.setStyle("-fx-font-family: Monospaced");
                if (resultSet.getString("available").equals("1"))
                    hBox.getChildren().addAll(bkName,genreName,libName,button);
                else{
                    if (resultSet.getString("cardNum").equals(libCardNum)){
                        //add code to return book
                        Button buttonReturn = new Button("Return");
                        buttonReturn.setOnMouseClicked(this::returnClick);
                        hBox.getChildren().addAll(bkName,genreName,libName,buttonReturn);
                    }
                    else{
                        Label label = new Label("Checked out");
                        label.setTextFill(Color.DARKBLUE);
                        hBox.getChildren().addAll(bkName,genreName,libName,label);
                    }
                }

                if (bkName.getText().length() >= bkLbl.getText().length()){
                    bkName.setText(bkName.getText().substring(0,bkLbl.getText().length()-4) + "...");
                }
                if (bkName.getText().length() <= bkLbl.getText().length()){
                    bkName.setText(format(bkName.getText(),
                            bkLbl.getText().length() - bkName.getText().length() + 2));
                }
                if (genreName.getText().length() <= gLbl.getText().length())
                    genreName.setText(format(genreName.getText(),
                            gLbl.getText().length() - genreName.getText().length() + 3));
                if (libName.getText().length() <= llLabel.getText().length())
                    libName.setText(format(libName.getText(),
                            llLabel.getText().length() - libName.getText().length()));
                hBox.setPrefWidth(BOXWIDTH);
                hBox.setPrefHeight(HBOXHEIGHT);
                resultVBox.getChildren().add(hBox);
                System.out.println(resultSet.getString("isbn") + "  " + resultSet.getString("bname") +
                        "  " + resultSet.getString("genre"));
            }
            System.out.println(bkcount);
            if (bkcount == 0){
                scrollPane.setOpacity(0);
                bkLbl.setOpacity(0);
                gLbl.setOpacity(0);
                llLabel.setOpacity(0);
                notifications.alertOk("","No books found");
            }
            else if (bkcount < 9){
                HBox hBox = new HBox();
                BackgroundFill bgfill = new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY);
                hBox.setBackground(new Background(bgfill));
                Label dummyLbl = new Label();
                dummyLbl.setText("");
                dummyLbl.setOpacity(0);
                dummyLbl.setPrefHeight(PANEHEIGHT - bkcount * HBOXHEIGHT);
                hBox.getChildren().add(dummyLbl);
                resultVBox.getChildren().add(hBox);
            }
        }
        catch (Exception e){
            scrollPane.setOpacity(0);
            bkLbl.setOpacity(0);
            gLbl.setOpacity(0);
            llLabel.setOpacity(0);
            notifications.alertOk("","No books found");
        }
    }

    private void buttonsAllClick(javafx.scene.input.MouseEvent mouseEvent) {
        //enter sql query to check out the book
        Button currentButton = (Button) mouseEvent.getSource();
        check_out_book(libCardNum,currentButton.getId());
        Button buttonReturn = new Button("Return");
        buttonReturn.setId(currentButton.getId());
        //
        HBox hBox = (HBox) currentButton.getParent();
        hBox.getChildren().remove(currentButton);
        //Label label = new Label("Checked out");
        //label.setTextFill(Color.DARKBLUE);
        buttonReturn.setOnMouseClicked(this::returnClick);
        hBox.getChildren().add(buttonReturn);
        Notifications notifications = new Notifications();
        notifications.alertOk("Book Status", "Book has been checked out");
    }
    private void returnClick(javafx.scene.input.MouseEvent mouseEvent){
        Button currentButton = (Button) mouseEvent.getSource();
        Notifications notifications = new Notifications();
        notifications.alertOk("Book Status", "Book has been returned");
        Button buttonCheckOut = new Button("Checkout");
        buttonCheckOut.setId(currentButton.getId());
        buttonCheckOut.setOnMouseClicked(this::buttonsAllClick);
        HBox hBox = (HBox) currentButton.getParent();
        hBox.getChildren().remove(currentButton);
        hBox.getChildren().add(buttonCheckOut);
    }

    @FXML
    void clearOnAction(ActionEvent event){
        bkNameTxt.clear();
        genreChoice.setValue("Genre Options");
        locationChoice.setValue("Location Options");
    }
    public String format(String frmtStr, int spaces){
        String newStr = frmtStr;
        for (int x = 0; x < spaces; x++){
            newStr = newStr + " ";
        }
        return newStr;
    }
    public String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    //region SQL
    public ResultSet search_book(String genre, String book_title, String isbn, Integer lib_id) {
        try {
            ResultSet result;
            String book_query = "select * from Books where genre like ? and bname like ? and isbn like ? and lid like ?";
            PreparedStatement prepared_query = connect.prepareStatement(book_query);
            prepared_query.setString(1, genre.isEmpty() ? "%" : genre);
            prepared_query.setString(2, book_title.isEmpty() ? "%" : "%" + book_title + "%");
            prepared_query.setString(3, isbn.isEmpty() ? "%" : isbn);
            prepared_query.setString(4, lib_id == null ? "%" : lib_id.toString());
            result = prepared_query.executeQuery();
            return result;
        } catch (Exception e) { /*Ignore */ }
        return null;
    }

    public Integer get_library_id(String llocation) {
        Integer libraryId = null;
        try {
            String library_query = "select lid from Libraries where llocation = ?";
            PreparedStatement prepared_query = connect.prepareStatement(library_query);
            prepared_query.setString(1, llocation);
            ResultSet result = prepared_query.executeQuery();
            if (result.next()) {
                libraryId = result.getInt("lid");
            }
        } catch (Exception e) { /*Ignore */ }
        return libraryId;
    }

    public String get_library_name(Integer lid) {
        String libraryName = null;
        try {
            String library_query = "select llocation from Libraries where lid = ?";
            PreparedStatement prepared_query = connect.prepareStatement(library_query);
            prepared_query.setInt(1, lid);
            ResultSet result = prepared_query.executeQuery();
            if (result.next()) {
                libraryName = result.getString("llocation");
            }
        } catch (Exception e) { /*Ignore */ }
        return libraryName;
    }

    /*
    * check out a book
    * PARAMS: card_number, isbn
    * RETURN: true if book is checked out, false if book doesn't exist or is already checked out
    */
    public boolean check_out_book(String card_number, String isbn) {
        try {
            String check_out_query = "update Books set cardnum = ?, available = 0 where isbn = ? and available = 1";
            PreparedStatement prepared_query = connect.prepareStatement(check_out_query);
            prepared_query.setString(1, card_number);
            prepared_query.setString(2, isbn);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }
    //endregion
}

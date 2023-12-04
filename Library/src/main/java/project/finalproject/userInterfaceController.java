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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
    VBox resultVBox;
    @FXML
    ScrollPane scrollPane;
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
        //Color.DARKBLUE
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
        resultsBorderPane.setCenter(scrollPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        resultVBox = new VBox();
        //scrollPane.setContent(resultVBox);
        scrollPane.setPannable(false);
        //scrollPane.setOpacity(1);
        scrollPane.setBackground(new Background(toolBarFill));
        resultVBox.setBackground(new Background(toolBarFill));
        resultsBorderPane.setBackground(new Background(toolBarFill));

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

    }
}

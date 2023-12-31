package project.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


/*
this class if for pop up notifications. doesn't work directly with the .fxml files
meant to be used by the controllers.
 */
public class Notifications {
    @FXML
    public void alertOk(String title, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.OK);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    static String yesOrNo(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                contentText,ButtonType.YES,ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            return ButtonType.YES.getText();
        }
        else if (result.isPresent() && result.get() == ButtonType.NO){
            return ButtonType.NO.getText();
        }
        return "";
    }
}

package ro.visualDB.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class Controller {
    @FXML
    private Text actiontarget;

    @FXML
    private Window newWindow;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }

    @FXML protected void createnewWindow(ActionEvent event){
//        newWindow.show
    }


}

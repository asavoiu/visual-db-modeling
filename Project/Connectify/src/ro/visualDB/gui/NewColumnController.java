package ro.visualDB.gui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 09.07.2013
 * Time: 02:21
 * To change this template use File | Settings | File Templates.
 */
public class NewColumnController {

    @FXML
    TextField columnName;
    @FXML
    CheckBox isNullabe;
    @FXML
    TextField minimumLength;
    @FXML
    TextField maximumLength;
    @FXML
    ComboBox dataTypeCombo;

    Controller parentController;

    public Controller getParentController() {
        return parentController;
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }
}

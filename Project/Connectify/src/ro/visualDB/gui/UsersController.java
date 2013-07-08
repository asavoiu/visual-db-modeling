package ro.visualDB.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 25.06.2013
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class UsersController {

    @FXML
    TextArea textUsersList;

    String usersListText = "";

    public void setUsersList(String usersList) {
        this.usersListText = usersList;

        textUsersList.setText(usersList.toString());
    }
}

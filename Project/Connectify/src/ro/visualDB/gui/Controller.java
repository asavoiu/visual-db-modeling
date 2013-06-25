package ro.visualDB.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Controller {

    @FXML
    private Text actiontarget;

    @FXML
    private Window newWindow;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
//        actiontarget.setText("Sign in button pressed");

        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/NewConnection.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("Add New Remote");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML protected void openRemote(ActionEvent event){
        File file;
        FileChooser fileChooser = new FileChooser();


        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

//        labelFile.setText(file.getPath());

    }

    @FXML protected void openExportWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/ExportRemote.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("Export Script");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML protected void openVersioningWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/Versioning.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("Versioning System");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML protected void openUsersWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/Users.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("Users Management System");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML protected void openStatisticsWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/Statistics.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("Users Management System");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML protected void openGitPage(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/asavoiu/visual-db-modeling"));
    }

    @FXML protected void openAboutPage(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/About.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("About us");
        dialogue.setScene(scene);
        dialogue.show();
    }

}

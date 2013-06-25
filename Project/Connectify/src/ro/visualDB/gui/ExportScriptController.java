package ro.visualDB.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 24.06.2013
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class ExportScriptController {

    @FXML
    protected void saveRemote(ActionEvent event){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            SaveFile("file", file);
        }

        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
//            Logger.getLogger(JavaFX_Text.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

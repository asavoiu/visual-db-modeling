package ro.visualDB.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ro.visualDB.api.Api;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.query.SQLEngine;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 24.06.2013
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class ExportScriptController {
	Remote remote;
	
	@FXML
    private ChoiceBox<String> dbType;


    @FXML
    protected void saveRemote(ActionEvent event){
    	if (dbType.getValue() == null) {
			return;
		}
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null) {
        	String sql = "";
        	try {        		
				sql = Api.getCreateSQLScriptsOfTreeNodeAndChildren(remote, 
						dbType.getValue().equalsIgnoreCase("PostgreSQL") ? SQLEngine.POSTGRES : SQLEngine.MYSQL);
				SaveFile(sql, file);
			} catch (Exception e) {
				// TODO SHOW ERROR
				e.printStackTrace();
			}
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

	public Remote getRemote() {
		return remote;
	}

	public void setRemote(Remote remote) {
		this.remote = remote;
	}

}

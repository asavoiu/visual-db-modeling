package ro.visualDB.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.connection.PostgreSQLDatabaseConnection;
import ro.visualDB.sql.helpers.DBInfoProcessor;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLReader;
import ro.visualDB.xml.XMLWriter;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 24.06.2013
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
public class NewConnectionController {

    @FXML
    private TextField hostName;

    @FXML
    private TextField port;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField databaseName;

    @FXML
    private CheckBox sslActive;

    @FXML
    private ComboBox<String> dbType;

    @FXML protected void closeWindow(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML protected void okButton(ActionEvent event) throws Exception {

        if ((hostName.getText().equals("")) || (port.getText().equals("")) ||
                (username.getText().equals("")) || (password.getText().equals("")) ||
                (databaseName.getText().equals("")) || (dbType.getSelectionModel().getSelectedItem()==null)) {
            try {
                showWarning();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }else{
            System.out.println(hostName.getText()+" \n"+port.getText()+" \n"+username.getText()+" \n"+password.getText()+" \n"+databaseName.getText()+"\n -------- \n"+sslActive.isSelected()+" \n"+dbType.getSelectionModel().getSelectedItem());

            if(dbType.getSelectionModel().getSelectedItem().equals("PostgreSQL")){
                IDatabaseConnection dbConn = new PostgreSQLDatabaseConnection(
                        hostName.getText(),//"ec2-23-21-161-153.compute-1.amazonaws.com",
                        port.getText(),//"5432",
                        username.getText(),//"ikqepbqiwxmcwe",
                        password.getText(),//"cI6PNkfjz4SajHnobEeCHwmvfv",
                        databaseName.getText(),//"dbtooekfdenm82",
                        true
                );

                Remote rmt = new Remote();
                rmt.setHost("instance43492.db.xeround.com");
                rmt.setPort("8907");
                rmt.setUser("octavyan55");
                rmt.setPassword("q1w2e3");
                rmt.setDatabase("");
                DBInfoProcessor dbip = new DBInfoProcessor(rmt);

//            rmt.setHost("localhost");
//            rmt.setPort("3306");
//			rmt.setUser("root");
//			rmt.setPassword("");
//			rmt.setDatabase("pw");

                TreeNode tn = new TreeNode();
                tn.setValue(rmt);

                //export scriptul bazei
                //PostgreScriptWriter postgreScriptWriter = new PostgreScriptWriter(tn,"C:\\Users\\Auras\\Desktop\\script.sql");
                //postgreScriptWriter.writeScriptToFile();

//			tn.print(2);
                //Tree tree = new Tree(tn);
                XMLWriter.writeToFile("C:\\Users\\Bogdan\\Desktop\\test.xml", tn);
                TreeNode readTn = XMLReader.readFromFile("C:\\Users\\Bogdan\\Desktop\\test.xml");
                XMLWriter.writeToFile("C:\\Users\\Bogdan\\Desktop\\test2.xml", readTn);
                System.out.println("gata");
            }

            if(dbType.getSelectionModel().getSelectedItem().equals("MySQL")){

            }
        }
    }

    protected void showWarning() throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/Warning.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("Warning");
        dialogue.setScene(scene);
        dialogue.show();
    }

}

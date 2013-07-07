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
import javafx.stage.Stage;
import ro.visualDB.api.Api;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;

import java.io.IOException;
import java.util.ArrayList;

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

//        if ((hostName.getText().equals("")) || (port.getText().equals("")) ||
//                (username.getText().equals("")) || (password.getText().equals("")) ||
//                (databaseName.getText().equals("")) || (dbType.getSelectionModel().getSelectedItem()==null)) {
//            try {
//                showWarning();
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }else{
//            if(dbType.getSelectionModel().getSelectedItem().equals("PostgreSQL")){

                //Show "Connecting" window
                Stage dialogue = new Stage();
                Parent root = null;

                FXMLLoader loader = new FXMLLoader();
                root = FXMLLoader.load(getClass().getResource("Dialogues/Loading.fxml"));
                Scene scene = new Scene(root);

                dialogue.setTitle("Please wait | Connecting");
                dialogue.setScene(scene);
                dialogue.show();

                //Connecting
//                IDatabaseConnection dbConn = new PostgreSQLDatabaseConnection(
//                    hostName.getText(),//"ec2-23-21-161-153.compute-1.amazonaws.com",
//                    port.getText(),//"5432",
//                    username.getText(),//"ikqepbqiwxmcwe",
//                    password.getText(),//"cI6PNkfjz4SajHnobEeCHwmvfv",
//                    databaseName.getText(),//"dbtooekfdenm82",
//                    "ec2-23-21-161-153.compute-1.amazonaws.com",
//                    "5432",
//                    "ikqepbqiwxmcwe",
//                    "cI6PNkfjz4SajHnobEeCHwmvfv",
//                    "dbtooekfdenm82",
//                    true
//                );

//                DBInfoProcessor dbip = new DBInfoProcessor(dbConn);
//                Remote rmt = new Remote();
//                rmt.setHost(hostName.getText());
//                rmt.setPort(port.getText());
//                rmt.setUser(username.getText());
//                rmt.setPassword(password.getText());
//                rmt.setDatabase(databaseName.getText());
//                rmt.setDatabaseEngine(SQLEngine.POSTGRES);
//
//                TreeNode tn = new TreeNode();
//                tn.setValue(rmt);

//                dbip.buildTreeForRemoteConnectionTreeNode(tn);
                Remote rmt = new Remote();
                rmt.setHost("ec2-23-21-161-153.compute-1.amazonaws.com");
                rmt.setPort("5432");
                rmt.setUser("ikqepbqiwxmcwe");
                rmt.setPassword("cI6PNkfjz4SajHnobEeCHwmvfv");
                rmt.setDatabase("dbtooekfdenm82");
                rmt.setDatabaseEngine(SQLEngine.POSTGRES);

                TreeNode myTree = Api.importFromRemote(rmt);
//                System.out.println(treeNode.getChildren().get(0).getChildren());

//                ArrayList<TreeNode> currentNode=null;
//                System.out.println(myTree.getChildren());

//                currentNode = myTree.getChildren();

//                myTree.print(2);

//                for (TreeNode firstChildren : currentNode) {
//                    System.out.println(firstChildren.toString());
//
//                    ArrayList<TreeNode> seccondChildren = firstChildren.getChildren();
//
//                    for (TreeNode c : seccondChildren) {
//                        System.out.println(c);
//                        if(c.getChildrenCount()!=0){
//                            System.out.println(c.getChildren());
//                        }
//                    }
//                }

        /*
        this.print2(myTree);
         */

                dialogue.close();
                Node source = (Node)  event.getSource();
                Stage stage  = (Stage) source.getScene().getWindow();
                stage.close();

//                Controller controller = new Controller();
//                controller.testMe(new ActionEvent());

//            }
//
//            if(dbType.getSelectionModel().getSelectedItem().equals("MySQL")){
//                //Show "Connecting" window
//                Stage dialogue = new Stage();
//                Parent root = null;
//
//                FXMLLoader loader = new FXMLLoader();
//                root = FXMLLoader.load(getClass().getResource("Dialogues/Loading.fxml"));
//                Scene scene = new Scene(root);
//
//                dialogue.setTitle("Please wait | Connecting");
//                dialogue.setScene(scene);
//                dialogue.show();
//
//                //Connecting
//                IDatabaseConnection dbConn = new MySQLDatabaseConnection(
//                    hostName.getText(),//"instance43492.db.xeround.com",
//                    port.getText(),//"8907",
//                    username.getText(),//"octavyan55",
//                    password.getText(),//"q1w2e3",
//                    databaseName.getText()//"",
//                );
//                DBInfoProcessor dbip = new DBInfoProcessor(dbConn);
//                Remote rmt = new Remote();
//                rmt.setHost(hostName.getText());
//                rmt.setPort(port.getText());
//                rmt.setUser(username.getText());
//                rmt.setPassword(password.getText());
//                rmt.setDatabase(databaseName.getText());
//
//                TreeNode tn = new TreeNode();
//                tn.setValue(rmt);
//                dbip.buildTreeForRemoteConnectionTreeNode(tn);
//
//                dialogue.close();
//
//                Node source = (Node)  event.getSource();
//                Stage stage  = (Stage) source.getScene().getWindow();
//                stage.close();
//
//            }
//        }
    }


    public void print2(TreeNode tn) {
        ArrayList<TreeNode> databaseName = tn.getChildren();
        System.out.println(databaseName);

        ArrayList<TreeNode> schemaName = databaseName.get(0).getChildren();
        System.out.println(schemaName);

        ArrayList<TreeNode> tables = schemaName.get(0).getChildren();
        System.out.println(tables);

        for(int i=0; i<tables.size(); i++){
            if(!tables.get(i).toString().contains("_pkey")){
                System.out.println("Tabel = "+tables.get(i).toString());

                ArrayList<TreeNode> children = tables.get(i).getChildren();

                for(int j=0; j<children.size(); j++){
                    System.out.println("    "+children.get(j));
                }

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

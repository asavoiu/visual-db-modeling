package ro.visualDB.gui;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ro.visualDB.api.Api;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.Schema;
import ro.visualDB.sql.model.Table;
import ro.visualDB.versioning.Version;
import ro.visualDB.xml.TreeNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	ArrayList<Remote> remotes = new ArrayList<Remote>();
	
    @FXML
    private TreeView<String> treeViewRemote;

    @FXML
    private Tab tabRemote;

    @FXML private TitledPane table1;
    @FXML private TitledPane table2;
    @FXML private TitledPane table3;
    @FXML private TitledPane table4;
    @FXML private TitledPane table5;
    @FXML private TitledPane table6;
    @FXML private TitledPane table7;
    @FXML private TitledPane table8;
    @FXML private TitledPane table9;

    List<String> tableNames = new ArrayList<String>(10);
    private int linesNo=1;

    @FXML private Line line1;

    private int tablesNr=0;
    private int columnsNo=0;
    private int constraintsNo=0;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialogues/NewConnection.fxml"));
        root = (Parent)loader.load();
        Scene scene = new Scene(root);
        
        // set data on the parentController
        NewConnectionController controller = loader.<NewConnectionController>getController();
        controller.setParentController(this);
        controller.getHostName().setText("ec2-23-21-161-153.compute-1.amazonaws.com");
        controller.getPort().setText("5432");
        controller.getUsername().setText("ikqepbqiwxmcwe");
        controller.getDatabaseName().setText("dbtooekfdenm82");
        controller.getPassword().setText("cI6PNkfjz4SajHnobEeCHwmvfv");
        controller.getDbType().setValue("PostgreSQL");
        controller.getSslActive().setSelected(true);
        
        dialogue.setTitle("Add New Remote");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML 
    protected void openRemote(ActionEvent event) throws Exception {
        File file;
        FileChooser fileChooser = new FileChooser();


        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        Remote myImportedSchema = (Remote)(Api.importFromXML(file.getAbsolutePath()));

        addRemote(myImportedSchema);

        printTreeInTreeView(event);
    }

    @FXML 
    protected void openExportWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialogues/ExportRemote.fxml"));
        root = (Parent)loader.load();
        Scene scene = new Scene(root);

        // set data on the parentController
        ExportScriptController controller = loader.<ExportScriptController>getController();
        controller.setRemote(remotes.size() > 0 ? remotes.get(remotes.size() - 1) : null);
        	  
        dialogue.setTitle("Export Script");
        dialogue.setScene(scene);
        dialogue.show();
    }

    public ArrayList<Version> loadVersions() {
    	String dirName = "C:\\versioning";
    	ArrayList<Version> versions = new ArrayList<Version>();
    	File dir = new File(dirName);
    	if (!dir.isDirectory()) {
    		return versions;
    	}
    	
    	for (File f : dir.listFiles()) {
    		if (f.isDirectory()) {
    			continue;
    		}
    		if (!f.getAbsolutePath().endsWith(".xml")) {
    			continue;
    		}
    		try {
	    		Version vers = new Version();
	    		vers.setVersion(f.getName().substring(0, f.getName().length() - 4));
	    		vers.setRemote((Remote)Api.importFromXML(f.getAbsolutePath()));
	    		versions.add(vers);
    		} catch (Exception e) {
    			// TODO show message
    			e.printStackTrace();
    		}
    	}
    	return versions;
    }
    
    @FXML
    protected void openVersioningWindow(ActionEvent event) throws IOException {
    	if (remotes.size() == 0) {
    		return;
    	}
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialogues/Versioning.fxml"));
        root = (Parent)loader.load();
        
        // set data on the parentController
        VersioningController controller = loader.<VersioningController>getController();
        controller.setRemote(remotes.size() > 0 ? remotes.get(remotes.size() - 1) : null);
        controller.setParentController(this);
        ArrayList<Version> versions = loadVersions();
        controller.setVersions(versions);
        
        TreeItem rootItem = new TreeItem("No difference");
        controller.treeViewRemote.setRoot(rootItem);
        Scene scene = new Scene(root);
        
        //set the versions in the checkBoxes
        ObservableListWrapper<Version> versionItems = new ObservableListWrapper<Version>(versions);
        controller.sourceVersionCb.setItems(versionItems);
        controller.destinationVersionCb.setItems(versionItems);

        dialogue.setTitle("Versioning System");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML 
    protected void openUsersWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialogues/Users.fxml"));
        root = (Parent)loader.load();

        UsersController usersController = loader.<UsersController>getController();

        String usersLists="";

        try {
            FileReader fstream = new FileReader("C:\\licenta\\postgresql.txt");

            BufferedReader in = new BufferedReader(fstream);

            String s;
            while((s = in.readLine()) != null){
                usersLists +="\t"+ s + "\n";
            }

        } catch (IOException e) {
            System.out.println("crapaaaaa - nu atinge!");
        }

        usersController.setUsersList(usersLists);

        Scene scene = new Scene(root);

        dialogue.setTitle("Users Management System");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML 
    protected void openStatisticsWindow(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialogues/Statistics.fxml"));
        root = (Parent)loader.load();

        StatisticsController statisticsController = loader.<StatisticsController>getController();

        statisticsController.populatePieChart(tablesNr,columnsNo,constraintsNo);


        Scene scene = new Scene(root);

        dialogue.setTitle("Users Management System");
        dialogue.setScene(scene);
        dialogue.show();
    }

    @FXML 
    protected void openGitPage(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/asavoiu/visual-db-modeling"));
    }

    @FXML 
    protected void openAboutPage(ActionEvent event) throws IOException {
        Stage dialogue = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("Dialogues/About.fxml"));
        Scene scene = new Scene(root);

        dialogue.setTitle("About us");
        dialogue.setScene(scene);
        dialogue.show();
    }

    String test="";

    @FXML 
    public void printTreeInTreeView(ActionEvent event) throws Exception {

        Remote myTree = remotes.size() > 0 ? remotes.get(remotes.size() - 1) : null;
        
        if (myTree == null) {
        	return;
        }

        final ContextMenu cm = new ContextMenu();
    	final MenuItem cmItem1 = new MenuItem("Add Column");
        final Controller newController = this;
        
    	cmItem1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage dialogue = new Stage();
                Parent root = null;
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialogues/newColumn.fxml"));
                try {
                    root = (Parent)loader.load();
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    return;
                }

                NewColumnController newColumnController = loader.<NewColumnController>getController();
                newColumnController.setParentController(newController);
                newColumnController.setTable((Table)cmItem1.getUserData());

                Scene scene = new Scene(root);

                dialogue.setTitle("Users Management System");
                dialogue.setScene(scene);
                dialogue.show();
            }
        });

    	cm.getItems().add(cmItem1);
    	
        treeViewRemote.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cm.hide();
                if (event.getButton() == MouseButton.SECONDARY) {
                    TreeItem item = treeViewRemote.getSelectionModel().getSelectedItem();
                    Object value = item.getValue();
                    if (value instanceof Table) {
                        cm.show(treeViewRemote, event.getScreenX(), event.getScreenY());
                        cmItem1.setUserData(value);
                    }
                }
            }
        });
        
        table1.setVisible(false);
        table2.setVisible(false);
        table3.setVisible(false);
        table4.setVisible(false);
        table5.setVisible(false);
        table6.setVisible(false);
        table7.setVisible(false);
        table8.setVisible(false);
        table9.setVisible(false);

        table1.setMinHeight(200);
        table1.setMinWidth(150);
        table2.setMinHeight(200);
        table2.setMinWidth(150);
        table3.setMinHeight(200);
        table3.setMinWidth(150);
        table4.setMinHeight(200);
        table4.setMinWidth(150);
        table5.setMinHeight(200);
        table5.setMinWidth(150);
        table6.setMinHeight(200);
        table6.setMinWidth(150);
        table7.setMinHeight(200);
        table7.setMinWidth(150);
        table8.setMinHeight(200);
        table8.setMinWidth(150);
        table9.setMinHeight(200);
        table9.setMinWidth(150);

        //database name - root
        ArrayList<TreeNode> databases = myTree.getChildren();
        TreeItem dbName = new TreeItem(databases.get(0));
        tabRemote.setText(myTree.toString());
        dbName.setExpanded(true);

        //schema name
        ArrayList<TreeNode> schemas = databases.get(0).getChildren();

        TreeItem schName = null;
        ArrayList<TreeNode> tables = null;

        if(((Schema)schemas.get(0)).getSchemaName().equalsIgnoreCase("INFORMATION_SCHEMA")){
            schName = new TreeItem(schemas.get(1));
            tables = schemas.get(1).getChildren();
        }else{
            schName = new TreeItem(schemas.get(0));
            tables = schemas.get(0).getChildren();
        }
        schName.setExpanded(true);
        dbName.getChildren().add(schName);

        //table names
        int tablesNo=0;

        for(int i=0; i<tables.size(); i++){

        if(!tables.get(i).toString().contains("_pkey")){
            tablesNr++;
            tablesNo++;
        TreeItem tableName = new TreeItem((Table)tables.get(i));

        ArrayList<TreeNode> children = tables.get(i).getChildren();

        String columns="";
        for(int j=0; j<children.size(); j++){
            columnsNo++;
            TreeItem itemCol = new TreeItem((Column)children.get(j));
            tableName.getChildren().add(itemCol);
            
            columns += ((Column)children.get(j)).getColumnName() + "\n";


            if(((Column)children.get(j)).getConstraint()!=null){
                constraintsNo++;
            }
//                System.out.println(((Column)children.get(j)).getColumnName() + " from table " + ((Column)children.get(j)).getTableName() +
//                    " to " + ((Column)children.get(j)).getConstraint().getTableName()
//                );

                //printLines(tables.get(i).toString()+"", ((Column)children.get(j)).getColumnName(), ((Column)children.get(j)).getConstraint().getTableName(), ((Column)children.get(j)).getConstraint().getColumnName());

//                line1.setStartX(table2.getLayoutX());
//                line1.setStartY(table2.getLayoutY());
//                line1.setEndX(table1.getLayoutX());
//                line1.setEndY(table1.getLayoutY());
//
//                line1.toBack();
//            }*/

            //Test lines
//            line1.setVisible(true);
//            line1.setStrokeWidth(2);
//            line1.setStartX(0);
//            line1.setStartY(0);
//            line1.setEndX(1000);
//            line1.setEndY(1000);

        }

        schName.getChildren().add(tableName);
        tableName.setExpanded(true);

                  //----------visual edit side-----------
                  switch (tablesNo) {
                      case 1: table1.setVisible(true);
                              table1.setText(tables.get(i).toString() + "");
                              table1.setContent(new Label(columns));
                              table1.setMinWidth(160);
                              table1.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 2: table2.setVisible(true);
                              table2.setText(tables.get(i).toString() + "");
                              table2.setContent(new Label(columns));
                              table2.setMinWidth(160);
                              table2.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 3: table3.setVisible(true);
                              table3.setText(tables.get(i).toString() + "");
                              table3.setContent(new Label(columns));
                              table3.setMinWidth(160);
                              table3.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 4: table4.setVisible(true);
                              table4.setText(tables.get(i).toString() + "");
                              table4.setContent(new Label(columns));
                              table4.setMinWidth(160);
                              table4.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 5: table5.setVisible(true);
                              table5.setText(tables.get(i).toString() + "");
                              table5.setContent(new Label(columns));
                              table5.setMinWidth(160);
                              table5.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 6: table6.setVisible(true);
                              table6.setText(tables.get(i).toString() + "");
                              table6.setContent(new Label(columns));
                              table6.setMinWidth(160);
                              table6.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 7: table7.setVisible(true);
                              table7.setText(tables.get(i).toString() + "");
                              table7.setContent(new Label(columns));
                              table7.setMinWidth(160);
                              table7.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 8: table8.setVisible(true);
                              table8.setText(tables.get(i).toString() + "");
                              table8.setContent(new Label(columns));
                              table8.setMinWidth(160);
                              table8.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                      case 9: table9.setVisible(true);
                              table9.setText(tables.get(i).toString() + "");
                              table9.setContent(new Label(columns));
                              table9.setMinWidth(160);
                              table9.setMinHeight(80);
                              tableNames.add(tables.get(i).toString() + "");
                              break;
                  }
              }
          }

          treeViewRemote.setRoot(dbName);

          //Call draggable method
          dragMyTables(table1);
          dragMyTables(table2);
          dragMyTables(table3);
          dragMyTables(table4);
          dragMyTables(table5);
          dragMyTables(table6);
          dragMyTables(table7);
          dragMyTables(table8);
          dragMyTables(table9);
        System.out.println(tableNames);
      }

    public void dragMyTables(final TitledPane tableToMove){

        final double[] x = {0};
        final double[] y = {0};
        final double[] mousex = {0};
        final double[] mousey = {0};


        tableToMove.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dragboard db = tableToMove.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(tableToMove.getContent()));
                System.out.println(tableToMove.getText());

                mousex[0] = mouseEvent.getSceneX();
                mousey[0] = mouseEvent.getSceneY();
                //get the x and y position measure from Left-Top
                x[0] = tableToMove.getLayoutX();
                y[0] = tableToMove.getLayoutY();

                db.setContent(content);
                mouseEvent.consume();

//                line1.setStartX(table2.getLayoutX());
//                line1.setStartY(table2.getLayoutY());
//                line1.setEndX(table1.getLayoutX());
//                line1.setEndY(table1.getLayoutY());
            }
        });

        tableToMove.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

                x[0] += event.getSceneX() - mousex[0];
                y[0] += event.getSceneY() - mousey[0] ;
                //set the positon of Node after calculation
                tableToMove.setLayoutX(x[0]);
                tableToMove.setLayoutY(y[0]);
                //again set current Mouse x AND y position
                mousex[0] = event.getSceneX();
                mousey[0] = event.getSceneY();
                event.consume();
//                line1.setStartX(table2.getLayoutX());
//                line1.setStartY(table2.getLayoutY());
//                line1.setEndX(table1.getLayoutX());
//                line1.setEndY(table1.getLayoutY());
            }
        });

    }

    private void printLines(String initialTable, String initialColumn, String endTable, String endColumn) {
        if(linesNo==1){
            line1.setVisible(true);
//            System.out.println(tableNames + "\tcaut "+ initialTable + " in " + tableNames.indexOf("games") + " "+tableNames.get(0));

//            line1.setStartX();
//            line1.setStartY();
        }
    }
    
    public void addRemote (Remote remote) {
    	remotes.add(remote);
    }

}
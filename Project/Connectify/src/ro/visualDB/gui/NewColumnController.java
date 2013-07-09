package ro.visualDB.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.Table;

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
    CheckBox isNullable;
    @FXML
    ComboBox dataTypeCombo;

    Controller parentController;
    
    Table table;
    
    @FXML
    protected void closeWindow(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void addColumn(ActionEvent event) throws Exception {
    	if (dataTypeCombo.getValue() == null) {
    		return;
    	}
    	Column c = new Column();
    	c.setTableCatalogName(table.getTableCatalogName());
    	c.setTableSchemaName(table.getTableSchemaName());
    	c.setTableName(table.getTableName());
    	c.setColumnName(columnName.getText());
    	c.setIsNullable(isNullable.isSelected() ? "YES" : "NO");
    	c.setDataType(dataTypeCombo.getValue().toString());
    	table.addChild(c);
    	parentController.printTreeInTreeView(event);
    	closeWindow(event);
    }

    public Controller getParentController() {
        return parentController;
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
}

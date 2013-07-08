package ro.visualDB.gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ro.visualDB.api.Api;
import ro.visualDB.remotes.Remote;
import ro.visualDB.versioning.Version;
import ro.visualDB.xml.TreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 25.06.2013
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class VersioningController {
	private Remote remote;
	private Controller parentController;
	private ArrayList<Version> versions = new ArrayList<Version>();
	
	private static final ImageView addedIco = 
			new ImageView(new Image(VersioningController.class.getResourceAsStream("icons/added.png")));
	private static final ImageView dirtyIco = 
			new ImageView( new Image(VersioningController.class.getResourceAsStream("icons/dirty.png")));
	private static final ImageView removedIco = 
			new ImageView(new Image(VersioningController.class.getResourceAsStream("icons/removed.png")));
	
	static {
		final double size = 17.0; 
		addedIco.setFitWidth(size);
		addedIco.setFitHeight(size);
		dirtyIco.setFitWidth(size);
		dirtyIco.setFitHeight(size);
		removedIco.setFitWidth(size);
		removedIco.setFitHeight(size);
	}
	
	@FXML
	TreeView treeViewRemote;
	
	@FXML
	ChoiceBox<Version> sourceVersionCb;
	
	@FXML
	ChoiceBox<Version> destinationVersionCb;
	
	@FXML
	Button differencesBtn;
	
	public Remote getRemote() {
		return remote;
	}
	
	public void setRemote(Remote remote) {
		this.remote = remote;
	}

	public Controller getParentController() {
		return parentController;
	}

	public void setParentController(Controller parentController) {
		this.parentController = parentController;
	}

	public ArrayList<Version> getVersions() {
		return versions;
	}

	public void setVersions(ArrayList<Version> versions) {
		this.versions = versions;
	}
	  
    public  void addTreeNodeChildrenToRemoteTreeItem(TreeItem item, TreeNode node) {
    	for (TreeNode tn : node.getChildren()) {
    		TreeItem newItem;
    		if (tn.isAdded()) 
    			newItem = new TreeItem(tn + " (added)", addedIco);
    		else if (tn.isDeleted()) 
    			newItem = new TreeItem(tn + " (removed)", removedIco);
    		else if (tn.isDirty()) 
    			newItem = new TreeItem(tn + " (modified)", dirtyIco);
    		else 
        		newItem = new TreeItem(tn);
    		/*Text txt = new Text();
    		Label lbl = new Label();
    		lbl.setStyle("color:red");
    		newItem.setGraphic(lbl);*/
    		
    		item.getChildren().add(newItem);
    		addTreeNodeChildrenToRemoteTreeItem(newItem, tn);
    	}
    }
    
	@FXML 
    protected void checkVersions(ActionEvent event) throws IOException {
		if (sourceVersionCb.getValue() == null || destinationVersionCb.getValue() == null) {
			treeViewRemote.setRoot(new TreeItem("No difference"));
		} else {
			try {
				Version sourceVersion = sourceVersionCb.getValue();
				Version destVersion = destinationVersionCb.getValue();
				Remote rmt = (Remote) Api.getTreeWithFlags(sourceVersion.getRemote(),
						destVersion.getRemote());
		        TreeItem rootItem = new TreeItem(rmt.getHost());
		        addTreeNodeChildrenToRemoteTreeItem(rootItem, rmt);
		        treeViewRemote.setRoot(rootItem);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

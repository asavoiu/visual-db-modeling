package ro.visualDB.gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import ro.visualDB.remotes.Remote;
import ro.visualDB.versioning.Version;

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
	
	@FXML
	TreeView treeViewRemote;
	
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
	
	
}

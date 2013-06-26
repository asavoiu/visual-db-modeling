package ro.visualDB.versioning;

import java.util.ArrayList;

import ro.visualDB.api.Api;
import ro.visualDB.xml.TreeNode;

/**
 * For tables the best identifier is the Name because
 * the Table Name does not change unless you recreate
 * the Table.
 * For Columns we also use the Column Name as an
 * identifier but the name may change. (if it changes
 * we have no possibility of knowing that, and we consider
 * the column to be added as new, and the old one to be
 * deleted)
 * @author Auras
 *
 */
public class Versioning {

	/**
	 * Returns the TreeNode with the flags set for
	 * the difference between source and destination,
	 * where destination is the final version
	 * @param xmlSourceVersionFile
	 * @param xmlDestVersionFile
	 * @return
	 */	
	public static TreeNode getTreeWithFlags(String xmlSourceVersionFile,
			String xmlDestVersionFile) throws Exception {
		// Get the final tree node
		TreeNode destNode = Api.importFromXML(xmlDestVersionFile);
		// Get the source tree node
		TreeNode sourceNode = Api.importFromXML(xmlSourceVersionFile);
		
		destNode.addChildren(checkNodes(sourceNode, destNode));
		
        return destNode;
	}
	
	public static TreeNode getTreeWithFlags(TreeNode sourceNode,
			TreeNode destNode) throws Exception {
		destNode.addChildren(checkNodes(sourceNode, destNode));
        return destNode;
	}
	
	
	public static ArrayList<TreeNode> checkNodes(TreeNode sourceNode, TreeNode destNode) {
		// Check for the differences between the final
		// version and the older version
		// differences may be: added and altered nodes
		boolean found;
		for (TreeNode dstChild : destNode.children) {
			found = false;
			for (TreeNode srcChild : sourceNode.children) {
				// if we find the node
				if (dstChild.equalsName(srcChild)) {
					//check if it has been modified and set the apropriate flag
					if (!dstChild.equals(srcChild)) {
						dstChild.setDirty(true);
					}
					dstChild.addChildren(checkNodes(srcChild, dstChild));
					found = true;
					break;
				}
			}
			if (!found) {
				dstChild.setAdded(true);
			}
		}
		// check for nodes that were removed
		// the deleted nodes are found only in 
		// the source tree
		ArrayList<TreeNode> toBeAddedToDst = new ArrayList<TreeNode>(); 
		for (TreeNode srcChild : sourceNode.children) {
			found = false;
			for (TreeNode dstChild : destNode.children) {
				if (dstChild.equalsName(srcChild)) {
					found = true;
					break;
				}
			}
			if (!found) {
				// Set the flags and add
				// the node to the tree
				srcChild.setDeleted(true);
				toBeAddedToDst.add(srcChild);
			}
		}
		return toBeAddedToDst;
	}
	
	public static String differences(TreeNode tn) {
		String diff = "";
		for (TreeNode child : tn.getChildren()) {
			if (child.isAdded()) {
				diff += "NODE " + child + " ADDED\n";
			} else if (child.isDeleted()) {
				diff += "NODE " + child + " DELETED\n";
			} else if (child.isDirty()) {
				diff += "NODE " + child + " MODIFIED\n";
			}
			diff += differences(child);
		}
		return diff;
	}
}

package ro.visualDB.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Root Tree Node class
 * Convenience class -> does not do 
 * anything special, has a root TreeNode
 * (Database ??)
 * @author Auras
 *
 */
public class Tree implements XMLElement {
	TreeNode root;
	
	public Tree(Object value) {
		root = new TreeNode(value);
	}
	
	/**
	 * Adds <code>child</code> to root, as his child
	 * @param child
	 */
	public void addNode(TreeNode child){
		root.addChild(child);
	}
	
	public void print() {
		root.print(0);
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	/**
	 * Let the root TreeNode class build the XML
	 * representation
	 * @param doc the document the element will be part of
	 * @return a new Element to append to the doc
	 * @throws Exception
	 */
	public Element getDomElement(Document doc) throws Exception {
		return root.getDomElement(doc);
	}
}
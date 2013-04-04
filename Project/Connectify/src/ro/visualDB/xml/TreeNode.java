package ro.visualDB.xml;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** TreeNode class containing
 * the Value of the current node
 * and an ArrayList of his Children,
 * or an empty ArrayList 
 * if there are none.
 **/
public class TreeNode {
	private Object value;
	private ArrayList<TreeNode> children;
	
	public TreeNode() {
		this.value = null;
//		this.children = new ArrayList<>();
	}
	
	public TreeNode(Object value){
		this();
		this.value = value;	
	}
	
	public void addChild(TreeNode child) {
		children.add(child);
	}
	
	public int getChildrenCount() {
		return children.size();
	}
	
	/** Return the child from position <code>index</code>
	 * @param index Of the Child to return
	 * @return the child at <code>index</code>
	 * @throws IndexOutOfBoundsException 
	 * if the index is out of range (index < 0 || index >= size())	 
	 */
	public TreeNode getChildAt(int index) {
		/* Let exceptions like IndexOutOfBoundsException get thrown
		 * They must be handled outside this class
		 */
		return children.get(index);
	}
	
	/**
     * Returns the value.
	 * @return <code>value</code>
	 */
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
     * Returns the children of this Node.
	 * @return <code>children</code>
	 */
	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}
	
	public void print(int indentation) {
		for (int i = 0; i < indentation; i++) {
			System.out.print(" ");
		}
		System.out.println(value);
		for (TreeNode c : children) {
			c.print(indentation + 1);
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	/**
	 * Gets the XML Element representation of this treeNode
	 * The representation contains the node value and 
	 * its children appended to him. 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public Element getDomElement(Document doc) throws Exception {
		 Element el = doc.createElement(value.toString());
		 for (TreeNode t : getChildren()) {
			 domElementAddNode(doc, el, t);
		 }
		 return el;
	}
	
	/**
	 * Add an element to a root element.
	 * It also adds it's children as his child elements
	 * recursively.
	 * @param doc
	 * @param rootElement
	 * @param tn
	 * @throws Exception
	 */
	public void domElementAddNode(Document doc, Element rootElement, TreeNode tn) throws Exception {
		  Element elem = doc.createElement(tn.getValue().toString());
		  rootElement.appendChild(elem);
		  elem.normalize();
		  
		  for (TreeNode t : tn.getChildren()) {
			  domElementAddNode(doc, elem, t);
		  }
	}
}

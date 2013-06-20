package ro.visualDB.xml;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.logging.AppLogger;
import ro.visualDB.sql.query.SQLElement;

/** TreeNode class containing
 * the Value of the current node
 * and an ArrayList of his Children,
 * or an empty ArrayList 
 * if there are none.
 **/
public class TreeNode implements XMLElement, SQLElement {
	private Object value;
	public ArrayList<TreeNode> children;
	
	public TreeNode() {
		this.value = null;
		this.children = new ArrayList<TreeNode>();
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

	public Element getDomElement(Document doc) throws Exception {
		Element el;
		if (value instanceof XMLElement) {
			el = ((XMLElement) value).getDomElement(doc);
			for (TreeNode t : getChildren()) {
				el.appendChild(t.getDomElement(doc));
			}
		} else {
			AppLogger.getLogger().info("Tree node Value not instance of XMLElement");
			el = doc.createElement(value.toString());
			for (TreeNode t : getChildren()) {
				el.appendChild(t.getDomElement(doc));
			}
		}
		return el;
	}
	
	@Override
	public TreeNode parseElement(String uri, String localName, String qName,
			Attributes atts) {
		if (value instanceof XMLElement) {
			return ((XMLElement) value).parseElement(uri, localName, qName, atts);
		} else {
			AppLogger.getLogger().info("Tree node Value not instance of XMLElement");
			return null;
		}
	}

	@Override
	public String getSqlStatement(int sqlEngine) throws Exception {
		return "NO SLQ";
	}
}

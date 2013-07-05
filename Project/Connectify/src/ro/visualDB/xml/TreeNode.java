package ro.visualDB.xml;

import java.util.ArrayList;
import java.util.List;

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
	// ALTER is used when TABLE or COLUMN
	// properties have changed
	private boolean altered = false;
	// CHANGE is used when COLUMN description
	// has changed (including name)
	private boolean changed = false;
	// like CHANGE but only the description
	// changes(NULL/NOT NULL/TYPE etc.) but
	// not the name
	private boolean modified = false;
	
	/** For versioning **/
	// when a TreeNode is detected as a new node
	// the Versioning Module sets this flag to true
	private boolean added = false;
	// when a TreeNode is detected as removed
	// the Versioning Module sets this flag to true
	private boolean deleted = false;
	// when a TreeNode's properties have changed
	// the Versioning Module sets this flag to true
	// Example: a Column is altered from NOT NULL to NULL 
	private boolean dirty = false;

	
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
	
	public void addChildren(List<TreeNode> chlds) {
		children.addAll(chlds);
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
	public String getCreateSqlStatement(int sqlEngine) throws Exception {
		return "NO SLQ";
	}

	@Override
	public void setAltered(boolean altered) {
		this.altered = altered;
	}

	/**
	 * If the representation of this node has changed
	 * returns False, else True.
	 */
	@Override
	public boolean isAltered() {
		return altered;
	}
	
	@Override
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	@Override
	public boolean isChanged() {
		return changed;
	}
	
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}
	
	@Override
	public String getAlterSqlStatement(int sqlEngine) throws Exception {
		return "NO SLQ";
	}
	
	@Override
	public String getChangeSqlStatement(int sqlEngine) throws Exception {
		return "NO SLQ";
	}
	
	@Override
	public String getModifySqlStatement(int sqlEngine) throws Exception {
		return "NO SLQ";
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public boolean equalsName(TreeNode tn) {
		return false;
	}
}

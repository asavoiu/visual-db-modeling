package ro.visualDB.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

public interface XMLElement {
	
	/**
	 * Gets the XML org.w3c.dom.Element representation of the element
	 * implementing this interface. 
	 * @param doc org.w3c.dom.Document
	 * @return Element representation of the current item
	 * @throws Exception
	 */
	public Element getDomElement(Document doc) throws Exception;
	
	/**
	 * Returns a TreeNode representation of the element
	 * by building a new class for the Value Field and 
	 * filling it with values from "atts".
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param atts
	 * @return
	 */
	public TreeNode parseElement(String uri, String localName,
			String qName, Attributes atts);
}

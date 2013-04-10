package ro.visualDB.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLElement {
	
	/**
	 * Gets the XML org.w3c.dom.Element representation of the element
	 * implementing this interface. 
	 * @param doc org.w3c.dom.Document
	 * @return Element representation of the current item
	 * @throws Exception
	 */
	public Element getDomElement(Document doc) throws Exception;

}

package ro.visualDB.xml;

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import ro.visualDB.logging.AppLogger;

public class XMLContentHandler implements ContentHandler {
	private LinkedList<TreeNode> queue;
	TreeNode root;
	
	public XMLContentHandler() {
		queue = new LinkedList<TreeNode>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		queue.removeLast();
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		XMLElement el = XMLElementFactory.newXMLElementInstance(qName);
		if ( el != null ) {
			TreeNode tn = el.parseElement(uri, localName, qName, atts);
			if (queue.isEmpty()) { 
				queue.add(tn);
				// Add the first element as root
				root = tn;
			} else {
				TreeNode lastTn = queue.getLast();
				lastTn.addChild(tn);
				queue.addLast(tn);
			}
		} else {
			AppLogger.logger.error("Element Not found/recognized");
			System.out.println("Element Not found/recognized");
			// TODO we add a stub tree node to avoid exception when
			// doing queue.removeLast() in endElement() callback
			TreeNode tn = new TreeNode("Unknown");
			if (queue.isEmpty()) { 
				queue.add(tn);
				// Add the first element as root
				root = tn;
			} else {
				TreeNode lastTn = queue.getLast();
				lastTn.addChild(tn);
				queue.addLast(tn);
			}
		}		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
}

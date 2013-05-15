package ro.visualDB.xml;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class XMLReader {
	
	public static void writeToFile(String f, Tree t) throws Exception {
		  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		  //root elements
		  Document doc = docBuilder.newDocument();
		  Element rootElement = t.getDomElement(doc);
		  doc.appendChild(rootElement);
		  
		  //write the content into xml file
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  Transformer transformer = transformerFactory.newTransformer();
		  DOMSource source = new DOMSource(doc);
	      		  
		  StreamResult result =  new StreamResult(new File(f));
		  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		  transformer.transform(source, result);
	}
	
	public static TreeNode readFromFile(String sourceFile) throws Exception {
		 SAXParserFactory factory = SAXParserFactory.newInstance();
		 SAXParser parser = factory.newSAXParser();
		 org.xml.sax.XMLReader reader =  parser.getXMLReader();
		 InputSource source = new InputSource(new FileInputStream(new File(sourceFile)));
		 XMLContentHandler contentHandler = new XMLContentHandler();
		 reader.setContentHandler(contentHandler);
		 reader.parse(source);
		 return contentHandler.getRoot();
	}
}



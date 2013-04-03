package ro.dbviz.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {
	public static String INDENT_AMOUNT = "4";
	
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
		  transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", INDENT_AMOUNT);
		  transformer.transform(source, result);
	}
}

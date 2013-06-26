package ro.visualDB.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

public class XMLReader {
	
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
	
	public static TreeNode readFromString(String xmlText) throws Exception {
		 SAXParserFactory factory = SAXParserFactory.newInstance();
		 SAXParser parser = factory.newSAXParser();
		 org.xml.sax.XMLReader reader =  parser.getXMLReader();
		 InputSource source = new InputSource(new ByteArrayInputStream(xmlText.getBytes()));
		 XMLContentHandler contentHandler = new XMLContentHandler();
		 reader.setContentHandler(contentHandler);
		 reader.parse(source);
		 return contentHandler.getRoot();
	}
}



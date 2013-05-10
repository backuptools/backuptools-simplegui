package ch.fetm.backuptools.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.fetm.backuptools.common.BackupAgentConfig;

public abstract class BackupAgenConfigManager {
	public final static BackupAgentConfig readConfigurationFile() {
		BackupAgentConfig config = new BackupAgentConfig();
    	String home = System.getProperty("user.home");
    	Path path = Paths.get(home+"/backuptools");
    	
    	if(!path.toFile().exists()){
    		try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	Path configfile = Paths.get(path.toAbsolutePath().toString()+"/config.xml");
    	if(!configfile.toFile().exists()){
    		try {
				configfile.toFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder;
		try {
			builder = builderfactory.newDocumentBuilder();
			Document document = builder.parse(configfile.toFile());
        	document.getDocumentElement().normalize();
        	NodeList nodes = document.getElementsByTagName("config");
        	Node node = nodes.item(0);
        	if(node.getNodeType() == Node.ELEMENT_NODE)
        	{
        		Element element = (Element) node;
        		config.source_path = element.getElementsByTagName("source_path").item(0).getChildNodes().item(0).getNodeValue();
        		config.vault_path  = element.getElementsByTagName("vault_path").item(0).getChildNodes().item(0).getNodeValue();
        	}
    	} catch (Exception e) {
		e.printStackTrace();
		}
		return config;
	}

	public static void writeConfigurationInFile(BackupAgentConfig config) {
		String home = System.getProperty("user.home");
    	Path path = Paths.get(home+"/backuptools");
    	
    	if(!path.toFile().exists()){
    		try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	Path configfile = Paths.get(path.toAbsolutePath().toString()+"/config.xml");
    	if(!configfile.toFile().exists()){
    		try {
				configfile.toFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder;
		try {
			builder = builderfactory.newDocumentBuilder();
			Document document = builder.parse(configfile.toFile());
        	document.getDocumentElement().normalize();
        	NodeList nodes = document.getElementsByTagName("config");
        	Node node = nodes.item(0);
        	if(node.getNodeType() == Node.ELEMENT_NODE)
        	{
        		Element element = (Element) node;
        		element.getElementsByTagName("source_path").item(0).getChildNodes().item(0).setNodeValue(config.source_path);
        		element.getElementsByTagName("vault_path").item(0).getChildNodes().item(0).setNodeValue(config.vault_path);
        	}
    	} catch (Exception e) {
		e.printStackTrace();
		}
	}
}

package de.woock.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class StreetReader {
	
	private List<String> abc = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")); 
	private Random       rand = new Random();
	public  Map<String, Map<String, String>> streets = new HashMap<>();
	

	
	public void erstelleStrassenverzeichnisFuer(String stadt) {
		String                           directory    = "src/main/resources/static/streetnames";
		Map<String, String>              localStreets = new HashMap<>();
		
		abc.forEach(letter -> {
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder        dBuilder;
	    	Document               doc;
			try {
				String fileName = String.format(directory + "/%s_%s.txt", stadt, letter);
				File   file     = new File(fileName);

				dBuilder = dbFactory.newDocumentBuilder();
				doc      = dBuilder.parse(file);
				
				NodeList preList = doc.getElementsByTagName("tr");
				for (int i = 1; i < preList.getLength(); i++) {
					String street    = preList.item(i).getFirstChild().getFirstChild().getFirstChild().getTextContent();
					String stadtteil = preList.item(i).getFirstChild().getFirstChild().getFirstChild().getNextSibling().getTextContent();
					localStreets.put(street, stadtteil);
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				log.debug("keine Straßen zu {}, {} gefunden", stadt, letter);
			}
			streets.put(stadt, localStreets);
		});
	}
	
	public String zufallsStrasseInStadt(String stadt) {
		Map<String, String> strassenEinerStadt = streets.get(stadt);
		String[] alle = strassenEinerStadt.keySet().toArray(new String[0]);
		return alle[rand.nextInt(alle.length)];
	}
}

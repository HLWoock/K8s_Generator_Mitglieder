package de.woock.entity.mitglied;

import java.util.HashMap;
import java.util.Map;

public class VorwahlenMobil {
	public static Map<String, String> vorwahlen = new HashMap<>();
	
	static {
		vorwahlen.put("Telekom" , "0151");
		vorwahlen.put("Vodafone", "0152");
		vorwahlen.put("E-Plus"  , "0157");
		vorwahlen.put("O2"      , "0159");
	}
}

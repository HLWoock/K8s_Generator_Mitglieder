package de.woock.service;

import static de.woock.entity.personalien.Anrede.Frau;
import static de.woock.entity.personalien.Anrede.Herr;
import static de.woock.entity.personalien.Geschlecht.M;
import static de.woock.entity.personalien.Geschlecht.W;
import static de.woock.entity.status.Bonitaet.ANFRAGE_AUSSTEHEND;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import de.woock.domain.MitgliedsAntrag;
import de.woock.entity.mitglied.Mitglied;
import de.woock.entity.mitglied.Namen;
import de.woock.entity.mitglied.VorwahlenFestnetz;
import de.woock.entity.mitglied.VorwahlenMobil;
import de.woock.entity.personalien.Adresse;
import de.woock.entity.personalien.Anrede;
import de.woock.entity.personalien.Geschlecht;
import de.woock.entity.status.Bonitaet;
import de.woock.entity.status.Vertragsart;
import de.woock.remote.Verein;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class Generator {
	
	private final StreetReader  streetReader;
	private final Verein        verein;
	
    private Random            rand               = new Random();
    Map<String, Integer>      mitgliederProStadt = new HashMap<>();

	public void generiereMitglieder() {
		log.info("nehme Mitglieder auf");
		
//		mitgliederProStadt.put("Bremen"   ,  5);
//		mitgliederProStadt.put("Buchholz" ,  5);
		mitgliederProStadt.put("Hamburg"  ,  10);
//		mitgliederProStadt.put("Kiel"     ,  5);
//		mitgliederProStadt.put("Lübeck"   ,  5);
//		mitgliederProStadt.put("Lüneburg" ,  5);
//		mitgliederProStadt.put("Pinneberg",  5);
//		mitgliederProStadt.put("Reinbek"  ,  5);
//		mitgliederProStadt.put("Stade"    ,  5);
		
    	mitgliederProStadt.keySet().stream()
    	                           .forEach(ort -> IntStream.range(0, mitgliederProStadt.get(ort)).forEach(i -> {
    	                        	   Anrede     anrede       = anrede();
    	                        	   String     vorname      = vorname(anrede);
    	                        	   String     name         = name();
    	                        	   String     username     = username(name, vorname);
    	                        	   LocalDate  geburtstag   = geburtsdatum();
    	                        	   String     telefon      = getFestnetz(ort);
    	                        	   String     mobil        = getMobil();
    	                        	   String     email        = String.format("%s.%s@%s.de", vorname, name, Provider.values()[rand.nextInt(Provider.values().length)]);
    	                        	   Adresse    adresse      = adresse(ort);
    	                        	   Geschlecht geschlecht   = geschlecht(anrede);
    	                        	   Bonitaet   bonitaet     = ANFRAGE_AUSSTEHEND;
    	                        	   Vertragsart vertragsart = vertragsart();
    	                        	   
    	                        	   log.info("generiere Mitglied: {} {}", vorname, name);
    	                        	   Mitglied mitglied = Mitglied.builder().adresse(adresse)
    	                        	                                         .anrede(anrede)
    	                        	                                         .bonitaet(bonitaet)
    	                        	                                         .email(email)
    	                        	                                         .geschlecht(geschlecht)
    	                        	                                         .mobil(mobil)
    	                        	                                         .name(name)
    	                        	                                         .telefon(telefon)
    	                        	                                         .username(username)
    	                        	                                         .vertragsart(vertragsart)
    	                        	                                         .vorname(vorname)
    	                        	                                         .geburtsdatum(geburtstag)
    	                        	                               .build();
    	                        	   log.debug("Mitgliedsantrag für {} {} {}", anrede.name(), vorname, name);
    	                        	   MitgliedsAntrag mitgliedsantrag = new MitgliedsAntrag(mitglied.getVorname(), mitglied.getName(), mitglied.getAdresse().getOrt(), 
    	                        			                                                 mitglied.getAdresse().getStrasse(), mitglied.getTelefon(), mitglied.getEmail());
    	                        	   verein.submitAntrag(mitgliedsantrag );
    	                        	   
    	                           }));
    	log.info("Mitglieder aufgenommen");
	}

	private String username(String name, String vorname) {
		return vorname.substring(0, 2) + name + rand.nextInt(1000) ;
	}

	private Vertragsart vertragsart() {
		return rand.nextBoolean() ? Vertragsart.FIRMENKUNDE : Vertragsart.PRIVATKUNDE;
	}

	private Geschlecht geschlecht(Anrede anrede) {
		return anrede.equals(Herr) ? M: W;
	}

	private Adresse adresse(String ort) {
		String strasse = streetReader.zufallsStrasseInStadt(ort);
		return new Adresse(ort, String.format("%s %s", strasse, rand.nextInt(400)));
	}
	
    private Anrede anrede() {
        return rand.nextBoolean() ? Herr : Frau;
    }
    private String name() {
		int anzahlSure     = Namen.lastName.length;
        return Namen.lastName[rand.nextInt(anzahlSure-1)];
    }
	
	private String vorname(Anrede anrede) {
        int geschlecht = anrede.equals(Herr)? 0: 1;
        int size = Namen.name[geschlecht].length;
        return Namen.name[geschlecht][rand.nextInt(size)];
    }
	
    private LocalDate geburtsdatum() {
    	return LocalDate.ofYearDay(rand.nextInt(80) + 1936, rand.nextInt(365) + 1);
    }
    
    private String getMobil() {
    	int anzahlVorwahlen = VorwahlenMobil.vorwahlen.size();
    	return VorwahlenMobil.vorwahlen.values().toArray()[rand.nextInt(anzahlVorwahlen)] + " / " + (rand.nextInt(100000000)+10000000);
    }
    
    private String getFestnetz(String stadt) {
    	return VorwahlenFestnetz.vorwahlen.get(stadt) + " / " + (rand.nextInt(10000000)+1000000);
    }
    
	
	private enum Provider {
		gmail, web, aol, gmx, freemail, yahoo, hotmail, ionos, lavabit, mail
	}
}

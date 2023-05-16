package de.woock.domain;

import java.io.Serializable;

public record MitgliedsAntrag(String vorname,
		                      String name,
		                      String ort,
		                      String strasse,
		                      String telefon,
		                      String email) implements Serializable {
}

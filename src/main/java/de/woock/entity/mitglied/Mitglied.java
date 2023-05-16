package de.woock.entity.mitglied;

import static jakarta.persistence.EnumType.STRING;

import java.time.LocalDate;

import de.woock.entity.personalien.Adresse;
import de.woock.entity.personalien.Anrede;
import de.woock.entity.personalien.Geschlecht;
import de.woock.entity.status.Bonitaet;
import de.woock.entity.status.Vertragsart;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("serial")
@Builder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Mitglied extends Kopfdaten {
	
	public Mitglied(String username) {
        this.username = username;
	}
	@Enumerated(STRING)    Anrede      anrede;
						   String      vorname;
     					   String      name;
	@Column(unique = true) String      username;
	                       LocalDate   geburtsdatum;
	                       String      telefon;
	                       String      mobil;
    					   String      email;
    @Embedded              Adresse     adresse;
    @Enumerated(STRING)	   Geschlecht  geschlecht;
    @Enumerated(STRING)    Bonitaet    bonitaet;
    @Enumerated(STRING)    Vertragsart vertragsart;

}

package de.woock.entity.mitglied;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Anschrift {

	@Id
	@GeneratedValue
	private Long id;
	
	private String ort;
	private String strasse;
}

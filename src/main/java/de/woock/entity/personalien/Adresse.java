package de.woock.entity.personalien;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Adresse implements Serializable {

	private String ort;
	private String strasse;
}

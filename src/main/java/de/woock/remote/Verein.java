package de.woock.remote;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import de.woock.domain.MitgliedsAntrag;

public interface Verein {
	
	@PostExchange("/api/antrag")
	public void submitAntrag(@RequestBody MitgliedsAntrag antrag);

}

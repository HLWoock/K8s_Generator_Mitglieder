package de.woock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class SchufaService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	public void bonitaet(String name) {
		log.info("stelle Anfrage für {}", name);
		restTemplate.postForEntity("http://localhost:8091/api/anfrage", new Anfrage(name), Void.class);
	}


}

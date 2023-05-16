package de.woock;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import de.woock.remote.Verein;
import de.woock.service.Generator;
import de.woock.service.StreetReader;

@SpringBootApplication
public class MitgliederGenerator {
	
	private List<String> list = Arrays.asList("Hamburg Reinbek Lüneburg Pinneberg Buchholz Lübeck Kiel Stade Bremen".split(" ")); 


	public static void main(String[] args) {
		SpringApplication.run(MitgliederGenerator.class, args);
		

	}
	
	@Bean
	ApplicationRunner init(StreetReader reader, Generator generator) {
		return args -> {
			list.forEach(stadt -> reader.erstelleStrassenverzeichnisFuer(stadt));
			generator.generiereMitglieder();
		};
	}
	
	@Bean
	Verein verein() {
		WebClient wc = WebClient.builder()
                                .baseUrl("http://localhost:8081/")
                                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(wc))
                                                                 .build();
        return factory.createClient(Verein.class);		
	}

}

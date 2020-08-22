package io.github.kprasad99.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.kprasad99.config.PersonServiceProperties;
import io.github.kprasad99.endpoint.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(PersonServiceProperties.class)
public class PersonClient {

    @Autowired
    private PersonServiceProperties props;
	private WebClient client;

    private String getUrl() {
        return String.format("http://%s:%d%s", props.getHost(), props.getPorts().getHttp(), props.getPath());
    }

	@PostConstruct
	public void init() {
		client = WebClient.create(getUrl());
	}

	public Flux<Person> list() {
		return client.get().uri("/api/db/person").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(Person.class);
	}

	public Mono<Person> findById(int id) {
		return client.get().uri("/api/db/person/"+id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Person.class);
	}
}

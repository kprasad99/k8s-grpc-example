package io.github.kprasad99.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad99.client.PersonClient;
import io.github.kprasad99.endpoint.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/person")
@AllArgsConstructor
@Slf4j
public class PersonRestService {

    private final PersonClient client;

    @GetMapping
    public Flux<Person> list(){
        log.info("Listing persons");
        return client.list().subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/{id}")
    public Mono<Person> get(@PathVariable("id")int id){
        log.info("Retrieving info on person with id {}", id);
        return client.findById(id).subscribeOn(Schedulers.boundedElastic());
    }

}

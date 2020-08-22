package io.github.kprasad99.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad99.orm.dao.PersonDao;
import io.github.kprasad99.orm.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/db/person")
@AllArgsConstructor
@Slf4j
public class PersonRestService {

    private final PersonDao personDao;

    @GetMapping
    public Flux<Person> list(){
        log.info("Listing persons");
        return Flux.fromIterable(personDao.findAll());
    }

    @GetMapping("/{id}")
    public Mono<Person> get(@PathVariable("id")int id){
        log.info("Retrieving info on person with id {}", id);
        return Mono.fromSupplier(()->personDao.findById(id).orElse(null));
    }

}

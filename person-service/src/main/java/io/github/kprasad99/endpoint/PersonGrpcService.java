package io.github.kprasad99.endpoint;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad99.client.PersonGrpcClient;
import io.github.kprasad99.endpoint.model.Person;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/grpc/person")
public class PersonGrpcService {

    private final PersonGrpcClient client;
    @NonNull
    private ModelMapper mapper;

    @GetMapping("/list")
    public Flux<Person> list() {
        log.info("Listing grpc persons using list");
        return client.list().map(toModel).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/stream")
    public Flux<Person> listStream() {
        log.info("Listing grpc persons using streams");
        return client.listStream().map(toModel).subscribeOn(Schedulers.boundedElastic());
    }

    private Function<io.github.kprasad99.person.proto.PersonProto.Person, io.github.kprasad99.endpoint.model.Person> toModel = p -> mapper
            .map(p, Person.class);

}

package io.github.kprasad99.endpoint;

import java.util.function.Function;

import org.lognet.springboot.grpc.GRpcService;
import org.modelmapper.ModelMapper;

import com.google.protobuf.Empty;

import io.github.kprasad99.orm.dao.PersonDao;
import io.github.kprasad99.person.proto.PersonProto.Person;
import io.github.kprasad99.person.proto.PersonProto.Persons;
import io.github.kprasad99.person.proto.PersonServiceGrpc.PersonServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
@RequiredArgsConstructor
public class PersonGrpcService extends PersonServiceImplBase {

    private final PersonDao personDao;
    @NonNull
    private ModelMapper mapper;

    @Override
    public void listStream(Empty request, StreamObserver<Person> response) {
        log.info("Sending person list");
        personDao.findAll().stream().map(toProto).map(Person.Builder::build).forEach(response::onNext);
        response.onCompleted();
    }

    @Override
    public void list(Empty request, StreamObserver<Persons> response) {
        log.info("Sending person list");
        Persons.Builder persons = Persons.newBuilder();
        personDao.findAll().stream().map(toProto).map(Person.Builder::build).forEach(persons::addPerson);
        response.onNext(persons.build());
        response.onCompleted();
    }

    public Function<io.github.kprasad99.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
            Person.Builder.class);

}

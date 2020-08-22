package io.github.kprasad99.client;

import org.springframework.stereotype.Service;

import com.google.protobuf.Empty;

import io.github.kprasad99.StreamObserverPublisher;
import io.github.kprasad99.person.proto.PersonProto.Person;
import io.github.kprasad99.person.proto.PersonServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class PersonGrpcClient {

    private final ManagedChannel managedChannel;

    public Flux<Person> listStream() {
        StreamObserverPublisher<Person> streamObserverPublisher = StreamObserverPublisher.create();
        PersonServiceGrpc.newStub(managedChannel).listStream(Empty.newBuilder().build(), streamObserverPublisher);
        return streamObserverPublisher.toFlux();
    }

    public Flux<Person> list() {
        return Flux.fromIterable(
                PersonServiceGrpc.newBlockingStub(managedChannel).list(Empty.newBuilder().build()).getPersonList());
    }

}

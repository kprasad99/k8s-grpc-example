package io.github.kprasad99;

import io.grpc.stub.StreamObserver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.concurrent.Queues;

public final class StreamObserverPublisher<T> implements StreamObserver<T> {

    private UnicastProcessor<T> processor;

    public StreamObserverPublisher() {
        this.processor = UnicastProcessor.create(Queues.<T>unbounded(8).get());
    }

    public static <E> StreamObserverPublisher<E> create() {
        return new StreamObserverPublisher<>();
    }

    @Override
    public void onNext(T next) {
        processor.onNext(next);
    }

    @Override
    public void onError(Throwable throwable) {
        processor.onError(throwable);
    }

    @Override
    public void onCompleted() {
        processor.onComplete();
    }

    public Flux<T> toFlux() {
        return processor;
    }

}

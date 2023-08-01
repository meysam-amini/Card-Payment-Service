package com.meysam.transferservice.service.card;

import com.meysam.transferservice.model.entity.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService <T>{

    Mono<T> save(T object);
    Mono<Void> delete(Long id);
    Flux<T> findAll();
}

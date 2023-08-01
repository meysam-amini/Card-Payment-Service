package com.meysam.transferservice.repository;

import com.meysam.transferservice.model.entity.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CardRepository extends ReactiveCrudRepository<Card, Long> {

    Mono<Card> findCardByCardnumber(String cardNumber);

}

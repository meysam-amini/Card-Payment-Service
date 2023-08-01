package com.meysam.transferservice.service.card;

import com.meysam.transferservice.model.entity.Card;
import com.meysam.transferservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public Mono<Card> save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return cardRepository.deleteById(id);
    }
    @Override
    public Flux<Card> findAll() {
        return cardRepository.findAll();
    }

}

package com.meysam.transferservice.controller;


import com.meysam.transferservice.model.dto.CardDto;
import com.meysam.transferservice.model.entity.Card;
import com.meysam.transferservice.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/card-services/add")
@RequiredArgsConstructor
public class CardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;
    private final ModelMapper modelMapper;



    @PostMapping
    public Mono<ResponseEntity<CardDto>> create(@Valid @RequestBody Card card) {

        LOGGER.info("creating card: {}", card);
        return  cardService.save(card)
                .map(saved_card-> modelMapper.map(saved_card,CardDto.class))
                .map(saved_card_dto-> ResponseEntity.status(HttpStatus.CREATED)
                        .body(saved_card_dto));
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long id) {

        LOGGER.info("deleting card with id; {}", id);

        return  cardService.delete(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK)
                        .body("deleted: " + id)));
    }

    @GetMapping()
    public Flux<CardDto> findAll() {
        return  cardService.findAll().map(card -> modelMapper.map(card,CardDto.class));
    }

}

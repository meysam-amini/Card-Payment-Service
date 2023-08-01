package com.meysam.bankserviceprovider.controller;

import com.meysam.bankserviceprovider.model.TransferDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sp1/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeController.class);

    private final Environment environment;


    @PostMapping
    public Mono<Boolean> exchange(@RequestBody TransferDto transferDto) {

        boolean response= Boolean.parseBoolean(environment.getProperty("sp.exchange.response"));

        LOGGER.info("SP Response : "+response);
        LOGGER.info("Transfer Object : "+transferDto);

                    if (response)
                        return Mono.just(true);
                    else
                        return Mono.just(false);
    }
}
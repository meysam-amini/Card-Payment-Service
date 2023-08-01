package com.meysam.transferservice.service;

import com.meysam.transferservice.model.entity.Card;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class SaveService  {

    private final DatabaseClient databaseClient;
    Logger logger= LoggerFactory.getLogger(this.getClass());


    public  Mono<Boolean> saveCardOptimistic(Card object){
        return this.databaseClient
                .sql("UPDATE card SET balance = :newBalance, version= :newVersion WHERE id=:ID AND version=:oldVersion")
                .bind("newBalance", object.getBalance() )
                .bind("newVersion", object.getVersion()+1)
                .bind("ID",  object.getId() )
                .bind("oldVersion",  object.getVersion())
                .fetch().rowsUpdated()
               .flatMap(updated_count-> {
                    if(updated_count>0)
                        return Mono.just(true);
                    else return Mono.just(false);
                });
    }
}
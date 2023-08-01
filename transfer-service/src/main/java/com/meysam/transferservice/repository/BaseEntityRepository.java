package com.meysam.transferservice.repository;

import com.meysam.transferservice.model.entity.BaseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseEntityRepository<T extends BaseEntity> extends ReactiveCrudRepository<T ,Long> {
}

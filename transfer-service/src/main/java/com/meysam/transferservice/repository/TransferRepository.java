package com.meysam.transferservice.repository;

import com.meysam.transferservice.model.entity.Transfer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends ReactiveCrudRepository<Transfer,Long> {
}

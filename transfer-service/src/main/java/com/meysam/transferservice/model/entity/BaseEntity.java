package com.meysam.transferservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {
    @Id
    private long id;
    private long version;
}

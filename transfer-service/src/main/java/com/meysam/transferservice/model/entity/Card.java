package com.meysam.transferservice.model.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;


@Data
@Table
public class Card extends BaseEntity {

    private String fullname;

    private String cardnumber;

    private int cvv2;

    private int password1;

    private int password2;

    private String expiredate;

    private long balance;

    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return cardnumber.matches(card.cardnumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardnumber);
    }
}

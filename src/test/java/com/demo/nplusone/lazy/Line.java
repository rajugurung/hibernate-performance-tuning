package com.demo.nplusone.lazy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "LineNPlusOneLazy")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line {
    @Id
    private Long id;
    private String lineNbr;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
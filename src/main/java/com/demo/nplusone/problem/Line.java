package com.demo.nplusone.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line {
    @Id
    private Long id;
    private String lineNbr;

    @ToString.Exclude
    @ManyToOne
    private Order order;
}
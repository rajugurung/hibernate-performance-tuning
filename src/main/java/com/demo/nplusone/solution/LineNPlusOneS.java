package com.demo.nplusone.solution;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineNPlusOneS {
    @Id
    private Long id;
    private String lineNbr;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderNPlusOneS order;
}
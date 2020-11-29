package com.demo.onetomany;

import javax.persistence.Entity;
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
public class LineOneToM {
    @Id
    private Long id;
    private String lineNbr;

    @ToString.Exclude
    @ManyToOne
    private OrderOneToM order;
}
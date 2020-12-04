package com.demo.onetomany.bidirection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "LineBiDirection")
@Table(name = "LINE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Line {
    @Id
    @SequenceGenerator(name = "lineSeqGen", sequenceName = "lineSeq", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "lineSeqGen")
    private Long id;
    private String lineNbr;

    @ToString.Exclude
    @ManyToOne
    private Order order;
}

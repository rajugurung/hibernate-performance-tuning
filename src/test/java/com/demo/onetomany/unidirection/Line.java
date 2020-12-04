package com.demo.onetomany.unidirection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "LineUniDirection")
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

}

package com.demo.onetomany.bidirection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Order1ToMSoln")
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "mySeq", initialValue = 100, allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Line> lines = new ArrayList<>();

    public void addLine(Line line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(Line line) {
        lines.remove(line);
        line.setOrder(null);
    }
}

package com.demo.nplusone.eager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private Long id;
    private String orderNbr;
    private String custNbr;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
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
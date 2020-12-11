package com.demo.nplusone.lazy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "OrderNPlusOneLazy")
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order")
    private List<Line> lines = new ArrayList<>();
    @OneToMany(mappedBy = "order")

    public void addLine(Line line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(Line line) {
        lines.remove(line);
        line.setOrder(null);
    }

    
}
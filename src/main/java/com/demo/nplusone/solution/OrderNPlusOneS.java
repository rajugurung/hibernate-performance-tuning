package com.demo.nplusone.solution;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
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
public class OrderNPlusOneS {
    @Id
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order")
    private List<LineNPlusOneS> lines = new ArrayList<>();
    @OneToMany(mappedBy = "order")

    public void addLine(LineNPlusOneS line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(LineNPlusOneS line) {
        lines.remove(line);
        line.setOrder(null);
    }

    
}
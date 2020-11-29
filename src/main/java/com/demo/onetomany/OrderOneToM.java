package com.demo.onetomany;

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
public class OrderOneToM {
    @Id
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<LineOneToM> lines = new ArrayList<>();

    public void addLine(LineOneToM line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(LineOneToM line) {
        lines.remove(line);
        line.setOrder(null);
    }

    
}
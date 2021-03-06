package com.demo.cartesian.problem1;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "AttachmentCartProblem1")
@Table(name = "ATTACHMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    private Long id;
    private String location;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Order order;
}
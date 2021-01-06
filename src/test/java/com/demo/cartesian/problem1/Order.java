package com.demo.cartesian.problem1;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "OrderCartProblem1")
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
    private List<Attachment> attachments = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<Audit> audits = new ArrayList<>();

    public void addLine(Line line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(Line line) {
        lines.remove(line);
        line.setOrder(null);
    }

    public void addAudit(Audit audit) {
        audits.add(audit);
        audit.setOrder(this);
    }

    public void removeAudit(Audit audit) {
        audits.remove(audit);
        audit.setOrder(null);
    }

    public void addAttachment(Attachment attachment) {
        attachments.remove(attachment);
        attachment.setOrder(this);
    }

    public void removeAttachment(Attachment attachment) {
        attachments.remove(attachment);
        attachment.setOrder(null);
    }
}
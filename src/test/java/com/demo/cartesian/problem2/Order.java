package com.demo.cartesian.problem2;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "OrderCart")
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Line> lines = new HashSet<>();
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Attachment> attachments = new HashSet<>();
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Audit> audits = new HashSet<>();

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
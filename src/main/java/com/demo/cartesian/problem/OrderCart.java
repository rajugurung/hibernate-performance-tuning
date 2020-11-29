package com.demo.cartesian.problem;

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

@Entity
@Table(name = "ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCart {
    @Id
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<LineCart> lines = new HashSet<>();
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<AttachmentCart> attachments = new HashSet<>();
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<AuditCart> audits = new HashSet<>();

    public void addLine(LineCart line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(LineCart line) {
        lines.remove(line);
        line.setOrder(null);
    }

    public void addAudit(AuditCart audit) {
        audits.add(audit);
        audit.setOrder(this);
    }

    public void removeAudit(AuditCart audit) {
        audits.remove(audit);
        audit.setOrder(null);
    }

    public void addAttachment(AttachmentCart attachment) {
        attachments.remove(attachment);
        attachment.setOrder(this);
    }

    public void removeAttachment(AttachmentCart attachment) {
        attachments.remove(attachment);
        attachment.setOrder(null);
    }
}
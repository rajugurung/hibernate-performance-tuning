package com.demo.cartesian.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class OrderCartS {
    @Id
    private Long id;
    private String orderNbr;

    @OneToMany(mappedBy = "order")
    private List<LineCartS> lines = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<AttachmentCartS> attachments = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<AuditCartS> audits = new ArrayList<>();

    public void addLine(LineCartS line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void remove(LineCartS line) {
        lines.remove(line);
        line.setOrder(null);
    }

    public void addAudit(AuditCartS audit) {
        audits.add(audit);
        audit.setOrder(this);
    }

    public void removeAudit(AuditCartS audit) {
        audits.remove(audit);
        audit.setOrder(null);
    }

    public void addAttachment(AttachmentCartS attachment) {
        attachments.remove(attachment);
        attachment.setOrder(this);
    }

    public void removeAttachment(AttachmentCartS attachment) {
        attachments.remove(attachment);
        attachment.setOrder(null);
    }
}
package rw.ac.auca.ibuka_pay.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String method;

    private String note;

    private int amountPaid;

    private LocalDate paidDate;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getAmountPaid() { return amountPaid; }
    public void setAmountPaid(int amountPaid) { this.amountPaid = amountPaid; }

    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }

    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }
}

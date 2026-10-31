package rw.ac.auca.ibuka_pay.model;

import rw.ac.auca.ibuka_pay.enums.BillStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    private String reference;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    @ManyToOne
    @JoinColumn(name = "biller_id")
    private Biller biller;

    // --- Rich Domain Model methods ---
    // business logic lives here, not scattered across the service layer

    /**
     * Bills always start as UPCOMING regardless of what the caller sends.
     * Call this instead of setStatus when creating a new bill.
     */
    public void initializeAsNew() {
        this.status = BillStatus.UPCOMING;
    }

    /**
     * Returns true if this bill's dueDate is before today.
     * Service delegates date validation here.
     */
    public boolean isInThePast() {
        return this.dueDate != null && this.dueDate.isBefore(LocalDate.now());
    }

    /**
     * Core status transition logic. Returns null if the move is allowed,
     * or an error message string if it is not.
     * Keeps the transition table in one place — the entity itself.
     */
    public String tryTransitionTo(BillStatus newStatus) {
        if (this.status == BillStatus.UPCOMING) {
            if (newStatus == BillStatus.DUE || newStatus == BillStatus.VOIDED) {
                this.status = newStatus;
                return null; // null = success
            }
        }
        if (this.status == BillStatus.DUE) {
            if (newStatus == BillStatus.PAID || newStatus == BillStatus.VOIDED) {
                this.status = newStatus;
                return null;
            }
        }
        // PAID and VOIDED are terminal — nothing is allowed from them
        return "Cannot change status from " + this.status + " to " + newStatus;
    }

    /**
     * Applies non-status fields from another bill onto this one.
     * Status is intentionally excluded — use tryTransitionTo for that.
     */
    public void applyUpdatesFrom(Bill updated) {
        this.dueDate = updated.getDueDate();
        this.reference = updated.getReference();
        this.amount = updated.getAmount();
        this.subscriber = updated.getSubscriber();
        this.biller = updated.getBiller();
    }

    // --- standard getters/setters ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public BillStatus getStatus() { return status; }
    public void setStatus(BillStatus status) { this.status = status; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Subscriber getSubscriber() { return subscriber; }
    public void setSubscriber(Subscriber subscriber) { this.subscriber = subscriber; }

    public Biller getBiller() { return biller; }
    public void setBiller(Biller biller) { this.biller = biller; }
}

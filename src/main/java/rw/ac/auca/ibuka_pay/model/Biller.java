package rw.ac.auca.ibuka_pay.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "biller")
public class Biller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String companyName;
    private String contactName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "pay_code_id")
    private PayCode payCode;

    @ManyToMany
    @JoinTable(
        name = "biller_bill_category",
        joinColumns = @JoinColumn(name = "biller_id"),
        inverseJoinColumns = @JoinColumn(name = "bill_category_id")
    )
    private List<BillCategory> billCategories;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PayCode getPayCode() {
        return payCode;
    }

    public void setPayCode(PayCode payCode) {
        this.payCode = payCode;
    }

    public List<BillCategory> getBillCategories() {
        return billCategories;
    }

    public void setBillCategories(List<BillCategory> billCategories) {
        this.billCategories = billCategories;
    }
}
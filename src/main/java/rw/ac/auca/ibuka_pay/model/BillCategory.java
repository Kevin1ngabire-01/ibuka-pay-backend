package rw.ac.auca.ibuka_pay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bill_category")
public class BillCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "billCategories")
    private List<Biller> billers;

    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Biller> getBillers() { return billers; }
    public void setBillers(List<Biller> billers) { this.billers = billers; }
}
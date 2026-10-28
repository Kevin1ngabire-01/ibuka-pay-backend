package rw.ac.auca.ibuka_pay.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pay_code")
public class PayCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int codeNumber;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
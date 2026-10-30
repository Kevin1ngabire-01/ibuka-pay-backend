package rw.ac.auca.ibuka_pay.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "subscriber")
public class Subscriber {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String phone;

    private String email;

    public UUID getId() { return id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }
}

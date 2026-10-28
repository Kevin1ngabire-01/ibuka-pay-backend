package rw.ac.auca.ibuka_pay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ibuka_pay.model.Bill;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
}
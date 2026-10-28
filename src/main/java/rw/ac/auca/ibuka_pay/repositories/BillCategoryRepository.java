package rw.ac.auca.ibuka_pay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import java.util.UUID;

@Repository
public interface BillCategoryRepository extends JpaRepository<BillCategory, UUID> {
}
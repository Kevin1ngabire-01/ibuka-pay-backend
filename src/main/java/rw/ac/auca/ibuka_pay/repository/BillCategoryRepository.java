package rw.ac.auca.ibuka_pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.auca.ibuka_pay.model.BillCategory;

import java.util.List;
import java.util.UUID;

public interface BillCategoryRepository extends JpaRepository<BillCategory, UUID> {

    @Query("SELECT c FROM BillCategory c WHERE c.billers IS EMPTY")
    List<BillCategory> findUnusedCategories();
}

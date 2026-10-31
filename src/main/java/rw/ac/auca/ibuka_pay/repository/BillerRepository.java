package rw.ac.auca.ibuka_pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.ibuka_pay.model.Biller;
import rw.ac.auca.ibuka_pay.model.PayCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillerRepository extends JpaRepository<Biller, UUID> {

    @Query("SELECT b FROM Biller b JOIN b.billCategories c WHERE LOWER(c.name) = LOWER(:name)")
    List<Biller> findBillersByBillCategoryName(@Param("name") String name);

    boolean existsByPayCode(PayCode payCode);

    @Query("SELECT b FROM Biller b WHERE b.payCode.codeNumber = :codeNumber")
    Optional<Biller> findByPayCodeCodeNumber(@Param("codeNumber") int codeNumber);
}

package rw.ac.auca.ibuka_pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.auca.ibuka_pay.model.PayCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayCodeRepository extends JpaRepository<PayCode, UUID> {

    @Query("SELECT p FROM PayCode p WHERE NOT EXISTS (SELECT b FROM Biller b WHERE b.payCode = p) ORDER BY p.codeNumber ASC")
    List<PayCode> findFreePayCodes();

    Optional<PayCode> findByCodeNumber(int codeNumber);
}

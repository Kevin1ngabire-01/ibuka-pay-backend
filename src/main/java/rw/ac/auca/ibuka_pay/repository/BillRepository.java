package rw.ac.auca.ibuka_pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.ibuka_pay.enums.BillStatus;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.model.Biller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

    @Query("SELECT b FROM Bill b WHERE b.dueDate >= :start AND b.dueDate <= :end ORDER BY b.dueDate ASC")
    List<Bill> getBillsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    List<Bill> findByStatusOrderByDueDateAsc(BillStatus status);

    boolean existsByBillerAndDueDateAndStatusNot(Biller biller, LocalDate dueDate, BillStatus status);

    @Query("SELECT b.status, COUNT(b) FROM Bill b GROUP BY b.status")
    List<Object[]> countBillsByStatus();

    @Modifying
    @Query("UPDATE Bill b SET b.status = rw.ac.auca.ibuka_pay.enums.BillStatus.VOIDED WHERE b.biller.id = :billerId AND b.dueDate = :date AND b.status <> rw.ac.auca.ibuka_pay.enums.BillStatus.PAID")
    int cancelBillsForBillerOnDate(@Param("billerId") UUID billerId, @Param("date") LocalDate date);
}

package rw.ac.auca.ibuka_pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.ibuka_pay.model.Subscriber;

import java.util.List;
import java.util.UUID;

public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {

    List<Subscriber> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT b.subscriber FROM Bill b WHERE b.biller.id = :billerId")
    List<Subscriber> findSubscribersByBillerId(@Param("billerId") UUID billerId);
}

package rw.ac.auca.ibuka_pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p WHERE p.bill.subscriber.phone = :phone ORDER BY p.paidDate DESC")
    List<Payment> findPaymentsBySubscriberPhone(@Param("phone") String phone);

    boolean existsByBill(Bill bill);
}

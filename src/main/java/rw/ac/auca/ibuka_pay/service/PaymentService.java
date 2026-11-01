package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.enums.BillStatus;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.model.Payment;
import rw.ac.auca.ibuka_pay.repository.BillRepository;
import rw.ac.auca.ibuka_pay.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;

    public PaymentService(PaymentRepository paymentRepository, BillRepository billRepository) {
        this.paymentRepository = paymentRepository;
        this.billRepository = billRepository;
    }

    public String create(UUID billId, Payment payment) {
        Bill bill = billRepository.findById(billId).orElse(null);
        if (bill == null) {
            return "Bill not found";
        }
        if (bill.getStatus() != BillStatus.PAID) {
            return "Payments can only be recorded for PAID bills";
        }
        payment.setPaidDate(LocalDate.now());
        payment.setBill(bill);
        paymentRepository.save(payment);
        return "Payment saved";
    }

    public List<Payment> getBySubscriberPhone(String phone) {
        return paymentRepository.findPaymentsBySubscriberPhone(phone);
    }

    public String delete(UUID id) {
        if (paymentRepository.findById(id).isEmpty()) {
            return "Payment not found";
        }
        paymentRepository.deleteById(id);
        return "Deleted";
    }
}

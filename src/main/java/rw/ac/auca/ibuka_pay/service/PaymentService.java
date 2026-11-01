package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.enums.BillStatus;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.model.Payment;
import rw.ac.auca.ibuka_pay.repository.BillRepository;
import rw.ac.auca.ibuka_pay.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;

    public PaymentService(PaymentRepository paymentRepository, BillRepository billRepository) {
        this.paymentRepository = paymentRepository;
        this.billRepository = billRepository;
    }

    public String savePayment(UUID billId, Payment payment) {
        Optional<Bill> billOpt = billRepository.findById(billId);

        if (!billOpt.isPresent()) {
            return "The bill with that id does not exist";
        }

        Bill bill = billOpt.get();

        if (bill.getStatus() != BillStatus.PAID) {
            return "Payments are only for PAID bills";
        }

        payment.setPaidDate(LocalDate.now());
        payment.setBill(bill);
        paymentRepository.save(payment);
        return "Payment saved successfully";
    }

    public List<Payment> getPaymentsBySubscriberPhone(String phone) {
        return paymentRepository.findPaymentsBySubscriberPhone(phone);
    }

    public String deletePayment(UUID id) {
        Optional<Payment> found = paymentRepository.findById(id);
        // calling get() directly — fast path, no extra check
        if (!found.isPresent()) {
            return "The payment with that id does not exist";
        }
        Payment p = found.get();
        paymentRepository.delete(p);
        return "Payment deleted successfully";
    }
}

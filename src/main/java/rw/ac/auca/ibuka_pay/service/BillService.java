package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.auca.ibuka_pay.enums.BillStatus;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.repository.BillRepository;
import rw.ac.auca.ibuka_pay.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;

    public BillService(BillRepository billRepository, PaymentRepository paymentRepository) {
        this.billRepository = billRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public Bill getById(UUID id) {
        return billRepository.findById(id).orElse(null);
    }

    public String create(Bill bill) {
        if (bill.getDueDate() != null && bill.getDueDate().isBefore(LocalDate.now())) {
            return "Bill due date cannot be in the past";
        }
        boolean conflict = billRepository.existsByBillerAndDueDateAndStatusNot(
                bill.getBiller(), bill.getDueDate(), BillStatus.VOIDED);
        if (conflict) {
            return "Biller already has a bill due on that date";
        }
        bill.setStatus(BillStatus.UPCOMING);
        billRepository.save(bill);
        return "Bill saved successfully";
    }

    public String update(UUID id, Bill updated) {
        Bill existing = getById(id);
        if (existing == null) {
            return "Bill not found with id " + id;
        }
        existing.setDueDate(updated.getDueDate());
        existing.setReference(updated.getReference());
        existing.setAmount(updated.getAmount());
        existing.setSubscriber(updated.getSubscriber());
        existing.setBiller(updated.getBiller());
        billRepository.save(existing);
        return "Bill updated successfully";
    }

    public String updateStatus(UUID id, BillStatus newStatus) {
        Bill existing = getById(id);
        if (existing == null) {
            return "Bill not found with id " + id;
        }
        // check allowed transitions
        if (existing.getStatus() == BillStatus.UPCOMING) {
            if (newStatus == BillStatus.DUE || newStatus == BillStatus.VOIDED) {
                existing.setStatus(newStatus);
                billRepository.save(existing);
                return "Status updated";
            }
        }
        if (existing.getStatus() == BillStatus.DUE) {
            if (newStatus == BillStatus.PAID || newStatus == BillStatus.VOIDED) {
                existing.setStatus(newStatus);
                billRepository.save(existing);
                return "Status updated";
            }
        }
        return "Cannot change status from " + existing.getStatus() + " to " + newStatus;
    }

    public String delete(UUID id) {
        Bill bill = getById(id);
        if (bill == null) {
            return "Bill not found with id " + id;
        }
        if (paymentRepository.existsByBill(bill)) {
            return "Cannot delete a bill that has payments";
        }
        billRepository.deleteById(id);
        return "Bill deleted";
    }

    public List<Bill> getByStatus(BillStatus status) {
        return billRepository.findByStatusOrderByDueDateAsc(status);
    }

    public List<Bill> getBetweenDates(LocalDate start, LocalDate end) {
        return billRepository.getBillsInRange(start, end);
    }

    public List<Object[]> countByStatus() {
        return billRepository.countBillsByStatus();
    }

    @Transactional
    public int cancelForBillerOnDate(UUID billerId, LocalDate date) {
        return billRepository.cancelBillsForBillerOnDate(billerId, date);
    }
}

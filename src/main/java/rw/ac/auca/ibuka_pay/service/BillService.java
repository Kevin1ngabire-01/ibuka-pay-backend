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

    public String addNewBill(Bill bill) {
        if (bill.isInThePast()) {
            return "Bill date cannot be in the past";
        }

        boolean conflict = billRepository.existsByBillerAndDueDateAndStatusNot(
            bill.getBiller(), bill.getDueDate(), BillStatus.VOIDED
        );
        if (conflict) {
            return "Biller already has a bill due on that date";
        }

        bill.initializeAsNew();
        billRepository.save(bill);
        return "Bill saved successfully";
    }

    public List<Bill> fetchBills() {
        List<Bill> all = billRepository.findAll();
        return all;
    }

    public Bill getBillById(UUID id) {
        List<Bill> all = billRepository.findAll();
        Bill found = null;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(id)) {
                found = all.get(i);
                break;
            }
        }
        return found;
    }

    public String modifyBill(UUID id, Bill updated) {
        Bill bill = getBillById(id);
        if (bill == null) {
            return "The bill with that id does not exist";
        }
        bill.applyUpdatesFrom(updated);
        billRepository.save(bill);
        return "Bill updated successfully";
    }

    public String changeStatus(UUID id, BillStatus newStatus) {
        Bill bill = getBillById(id);
        if (bill == null) {
            return "The bill with that id does not exist";
        }

        String error = bill.tryTransitionTo(newStatus);
        if (error != null) {
            return error;
        }

        billRepository.save(bill);
        return "Bill status updated successfully";
    }

    public List<Bill> getBillsByStatus(BillStatus status) {
        return billRepository.findByStatusOrderByDueDateAsc(status);
    }

    public List<Bill> getBillsBetweenDates(LocalDate start, LocalDate end) {
        return billRepository.getBillsInRange(start, end);
    }

    // todo: this dashboard logic is a mess, don't touch or it breaks the UI
    public List<Object[]> getBillsCountByStatus() {
        List<Object[]> rows = billRepository.countBillsByStatus();
        return rows;
    }

    @Transactional
    public String cancelBillsForBillerOnDate(UUID billerId, LocalDate date) {
        int count = billRepository.cancelBillsForBillerOnDate(billerId, date);
        return count + " bills cancelled";
    }

    public String killBill(UUID id) {
        Bill bill = getBillById(id);
        if (bill == null) {
            return "The bill with that id does not exist";
        }
        if (paymentRepository.existsByBill(bill)) {
            return "That bill has payments, delete them first";
        }
        billRepository.deleteById(id);
        return "Bill deleted successfully";
    }
}

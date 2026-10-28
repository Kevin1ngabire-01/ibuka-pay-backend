package rw.ac.auca.ibuka_pay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.repositories.BillRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public String saveBill(Bill bill) {
        billRepository.save(bill);
        return "Bill saved successfully";
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(UUID id) {
        return billRepository.findById(id);
    }

    public String updateBill(UUID id, Bill updated) {
        Optional<Bill> existing = billRepository.findById(id);
        if (existing.isPresent()) {
            Bill bill = existing.get();
            bill.setDueDate(updated.getDueDate());
            bill.setStatus(updated.getStatus());
            bill.setReference(updated.getReference());
            bill.setAmount(updated.getAmount());
            bill.setSubscriber(updated.getSubscriber());
            bill.setBiller(updated.getBiller());
            billRepository.save(bill);
            return "Bill updated successfully";
        }
        return "The bill with that id does not exist";
    }

    public String deleteBill(UUID id) {
        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
            return "Bill deleted successfully";
        }
        return "The bill with that id does not exist";
    }
}
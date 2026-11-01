package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import rw.ac.auca.ibuka_pay.model.Biller;
import rw.ac.auca.ibuka_pay.model.PayCode;
import rw.ac.auca.ibuka_pay.repository.BillCategoryRepository;
import rw.ac.auca.ibuka_pay.repository.BillerRepository;
import rw.ac.auca.ibuka_pay.repository.PayCodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillerService {

    private final BillerRepository billerRepository;
    private final BillCategoryRepository billCategoryRepository;
    private final PayCodeRepository payCodeRepository;

    public BillerService(BillerRepository billerRepository,
                         BillCategoryRepository billCategoryRepository,
                         PayCodeRepository payCodeRepository) {
        this.billerRepository = billerRepository;
        this.billCategoryRepository = billCategoryRepository;
        this.payCodeRepository = payCodeRepository;
    }

    public List<Biller> getAll() {
        return billerRepository.findAll();
    }

    public Biller getById(UUID id) {
        return billerRepository.findById(id).orElse(null);
    }

    public Biller create(Biller biller) {
        return billerRepository.save(biller);
    }

    public Biller update(UUID id, Biller updated) {
        Biller existing = getById(id);
        if (existing == null) {
            return null;
        }
        existing.setCompanyName(updated.getCompanyName());
        existing.setContactName(updated.getContactName());
        existing.setEmail(updated.getEmail());
        return billerRepository.save(existing);
    }

    public String delete(UUID id) {
        if (getById(id) == null) {
            return "Biller not found";
        }
        billerRepository.deleteById(id);
        return "Deleted";
    }

    public List<Biller> getByCategory(String name) {
        return billerRepository.findBillersByBillCategoryName(name);
    }

    public String addCategory(UUID billerId, UUID billCategoryId) {
        Biller biller = getById(billerId);
        if (biller == null) {
            return "Biller not found";
        }
        BillCategory category = billCategoryRepository.findById(billCategoryId).orElse(null);
        if (category == null) {
            return "Bill category not found";
        }
        if (biller.getBillCategories().contains(category)) {
            return "Biller already has that category";
        }
        biller.getBillCategories().add(category);
        billerRepository.save(biller);
        return "Category added";
    }

    public String changePayCode(UUID billerId, int codeNumber) {
        Biller biller = getById(billerId);
        if (biller == null) {
            return "Biller not found";
        }
        Optional<PayCode> found = payCodeRepository.findByCodeNumber(codeNumber);
        if (found.isEmpty()) {
            return "Pay code not found";
        }
        PayCode payCode = found.get();
        if (billerRepository.existsByPayCode(payCode)) {
            return "That pay code is already taken";
        }
        biller.setPayCode(payCode);
        billerRepository.save(biller);
        return "Pay code updated";
    }
}

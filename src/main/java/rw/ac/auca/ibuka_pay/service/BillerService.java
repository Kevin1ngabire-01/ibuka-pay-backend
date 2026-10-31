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

    public BillerService(
            BillerRepository billerRepository,
            BillCategoryRepository billCategoryRepository,
            PayCodeRepository payCodeRepository) {
        this.billerRepository = billerRepository;
        this.billCategoryRepository = billCategoryRepository;
        this.payCodeRepository = payCodeRepository;
    }

    public String createBiller(Biller biller) {
        billerRepository.save(biller);
        return "Biller saved successfully";
    }

    public List<Biller> fetchAllBillers() {
        return billerRepository.findAll();
    }

    public Biller findBillerById(UUID id) {
        return billerRepository.findById(id).orElse(null);
    }

    public String editBiller(UUID id, Biller updated) {
        Biller biller = findBillerById(id);
        if (biller == null) {
            return "The biller with that id does not exist";
        }
        biller.setCompanyName(updated.getCompanyName());
        biller.setContactName(updated.getContactName());
        biller.setEmail(updated.getEmail());
        biller.setPayCode(updated.getPayCode());
        biller.setBillCategories(updated.getBillCategories());
        billerRepository.save(biller);
        return "Biller updated successfully";
    }

    public String removeBiller(UUID id) {
        if (billerRepository.findById(id).isEmpty()) {
            return "The biller with that id does not exist";
        }
        billerRepository.deleteById(id);
        return "Biller deleted successfully";
    }

    public List<Biller> getBillersByCategory(String name) {
        return billerRepository.findBillersByBillCategoryName(name);
    }

    public String addCategoryToBiller(UUID billerId, UUID billCategoryId) {
        Biller biller = findBillerById(billerId);
        if (biller == null) {
            return "The biller with that id does not exist";
        }

        BillCategory category = billCategoryRepository.findById(billCategoryId).orElse(null);
        if (category == null) {
            return "That bill category does not exist";
        }

        // Avoid adding the same category more than once.
        if (biller.getBillCategories().contains(category)) {
            return "The biller already has that bill category";
        }

        biller.getBillCategories().add(category);
        billerRepository.save(biller);
        return "Bill category added to the biller";
    }

    public String changePayCode(UUID billerId, int codeNumber) {
        Biller biller = findBillerById(billerId);
        if (biller == null) {
            return "The biller with that id does not exist";
        }

        Optional<PayCode> payCodeOpt = payCodeRepository.findByCodeNumber(codeNumber);
        if (payCodeOpt.isEmpty()) {
            return "The pay code with that codeNumber does not exist";
        }

        PayCode payCode = payCodeOpt.get();

        // A pay code can be assigned to only one biller.
        boolean taken = billerRepository.existsByPayCode(payCode);
        if (taken) {
            return "That pay code is taken by another biller";
        }

        biller.setPayCode(payCode);
        billerRepository.save(biller);
        return "Pay code changed successfully";
    }
}

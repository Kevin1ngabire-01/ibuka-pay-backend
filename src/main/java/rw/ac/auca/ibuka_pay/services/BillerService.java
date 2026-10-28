package rw.ac.auca.ibuka_pay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.Biller;
import rw.ac.auca.ibuka_pay.repositories.BillerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillerService {

    @Autowired
    private BillerRepository billerRepository;

    public String saveBiller(Biller biller) {
        billerRepository.save(biller);
        return "Biller saved successfully";
    }

    public List<Biller> getAllBillers() {
        return billerRepository.findAll();
    }

    public Optional<Biller> getBillerById(UUID id) {
        return billerRepository.findById(id);
    }

    public String updateBiller(UUID id, Biller updated) {
        Optional<Biller> existing = billerRepository.findById(id);
        if (existing.isPresent()) {
            Biller biller = existing.get();
            biller.setCompanyName(updated.getCompanyName());
            biller.setContactName(updated.getContactName());
            biller.setEmail(updated.getEmail());
            biller.setPayCode(updated.getPayCode());
            biller.setBillCategories(updated.getBillCategories());
            billerRepository.save(biller);
            return "Biller updated successfully";
        }
        return "The biller with that id does not exist";
    }

    public String deleteBiller(UUID id) {
        if (billerRepository.existsById(id)) {
            billerRepository.deleteById(id);
            return "Biller deleted successfully";
        }
        return "The biller with that id does not exist";
    }
}
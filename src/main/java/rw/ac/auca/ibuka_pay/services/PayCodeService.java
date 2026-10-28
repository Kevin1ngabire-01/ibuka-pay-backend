package rw.ac.auca.ibuka_pay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.PayCode;
import rw.ac.auca.ibuka_pay.repositories.PayCodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayCodeService {

    @Autowired
    private PayCodeRepository payCodeRepository;

    public String savePayCode(PayCode payCode) {
        payCodeRepository.save(payCode);
        return "Pay code saved successfully";
    }

    public List<PayCode> getAllPayCodes() {
        return payCodeRepository.findAll();
    }

    public Optional<PayCode> getPayCodeById(UUID id) {
        return payCodeRepository.findById(id);
    }

    public String updatePayCode(UUID id, PayCode updated) {
        Optional<PayCode> existing = payCodeRepository.findById(id);
        if (existing.isPresent()) {
            PayCode payCode = existing.get();
            payCode.setCodeNumber(updated.getCodeNumber());
            payCode.setName(updated.getName());
            payCodeRepository.save(payCode);
            return "Pay code updated successfully";
        }
        return "The pay code with that id does not exist";
    }

    public String deletePayCode(UUID id) {
        if (payCodeRepository.existsById(id)) {
            payCodeRepository.deleteById(id);
            return "Pay code deleted successfully";
        }
        return "The pay code with that id does not exist";
    }
}
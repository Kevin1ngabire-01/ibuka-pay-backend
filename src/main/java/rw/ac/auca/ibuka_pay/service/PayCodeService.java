package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.PayCode;
import rw.ac.auca.ibuka_pay.repository.PayCodeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PayCodeService {

    private final PayCodeRepository payCodeRepository;

    public PayCodeService(PayCodeRepository payCodeRepository) {
        this.payCodeRepository = payCodeRepository;
    }

    public String addPayCode(PayCode payCode) {
        try {
            payCodeRepository.save(payCode);
            return "Pay code saved successfully";
        } catch (Exception e) {
            return "Failed to save pay code: " + e.getMessage();
        }
    }

    public List<PayCode> getAllPayCodes() {
        try {
            return payCodeRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public PayCode getOnePayCode(UUID id) {
        try {
            PayCode pc = payCodeRepository.findById(id).orElse(null);
            if (pc == null) {
                return null;
            }
            return pc;
        } catch (Exception e) {
            return null;
        }
    }

    public String changePayCode(UUID id, PayCode updated) {
        try {
            PayCode payCode = payCodeRepository.findById(id).orElse(null);
            if (payCode == null) {
                return "The pay code with that id does not exist";
            }
            payCode.setCodeNumber(updated.getCodeNumber());
            payCode.setName(updated.getName());
            payCodeRepository.save(payCode);
            return "Pay code updated successfully";
        } catch (Exception e) {
            return "Update failed: " + e.getMessage();
        }
    }

    public String killPayCode(UUID id) {
        try {
            PayCode payCode = payCodeRepository.findById(id).orElse(null);
            if (payCode == null) {
                return "The pay code with that id does not exist";
            }
            payCodeRepository.deleteById(id);
            return "Pay code deleted successfully";
        } catch (Exception e) {
            return "Delete failed: " + e.getMessage();
        }
    }

    public List<PayCode> getFreePayCodes() {
        try {
            return payCodeRepository.findFreePayCodes();
        } catch (Exception e) {
            return null;
        }
    }
}

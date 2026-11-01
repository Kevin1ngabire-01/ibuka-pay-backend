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

    public List<PayCode> getAll() {
        return payCodeRepository.findAll();
    }

    public PayCode getById(UUID id) {
        return payCodeRepository.findById(id).orElse(null);
    }

    public PayCode create(PayCode payCode) {
        return payCodeRepository.save(payCode);
    }

    public PayCode update(UUID id, PayCode updated) {
        PayCode existing = getById(id);
        if (existing == null) {
            return null;
        }
        existing.setCodeNumber(updated.getCodeNumber());
        existing.setName(updated.getName());
        return payCodeRepository.save(existing);
    }

    public String delete(UUID id) {
        if (getById(id) == null) {
            return "Pay code not found";
        }
        payCodeRepository.deleteById(id);
        return "Deleted";
    }

    public List<PayCode> getFreePayCodes() {
        return payCodeRepository.findFreePayCodes();
    }
}

package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.repository.SubscriberRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public List<Subscriber> getAll() {
        return subscriberRepository.findAll();
    }

    public Subscriber getById(UUID id) {
        return subscriberRepository.findById(id).orElse(null);
    }

    public Subscriber create(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public Subscriber update(UUID id, Subscriber updated) {
        Subscriber existing = getById(id);
        if (existing == null) {
            return null;
        }
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        return subscriberRepository.save(existing);
    }

    public String delete(UUID id) {
        if (getById(id) == null) {
            return "Subscriber not found";
        }
        subscriberRepository.deleteById(id);
        return "Deleted";
    }

    public List<Subscriber> getByLastName(String lastName) {
        return subscriberRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Subscriber> getByBiller(UUID billerId) {
        return subscriberRepository.findSubscribersByBillerId(billerId);
    }
}

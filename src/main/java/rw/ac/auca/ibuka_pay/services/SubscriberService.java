package rw.ac.auca.ibuka_pay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.repositories.SubscriberRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public String saveSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
        return "Subscriber saved successfully";
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public Optional<Subscriber> getSubscriberById(UUID id) {
        return subscriberRepository.findById(id);
    }

    public String updateSubscriber(UUID id, Subscriber updated) {
        Optional<Subscriber> existing = subscriberRepository.findById(id);
        if (existing.isPresent()) {
            Subscriber sub = existing.get();
            sub.setFirstName(updated.getFirstName());
            sub.setLastName(updated.getLastName());
            sub.setPhone(updated.getPhone());
            sub.setEmail(updated.getEmail());
            subscriberRepository.save(sub);
            return "Subscriber updated successfully";
        }
        return "The subscriber with that id does not exist";
    }

    public String deleteSubscriber(UUID id) {
        if (subscriberRepository.existsById(id)) {
            subscriberRepository.deleteById(id);
            return "Subscriber deleted successfully";
        }
        return "The subscriber with that id does not exist";
    }
}
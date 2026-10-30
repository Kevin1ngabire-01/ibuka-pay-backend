package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.repository.SubscriberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public String registerSubscriber(Subscriber subscriber) {
        subscriber.setId(UUID.randomUUID());
        subscriberRepository.save(subscriber);
        return "Subscriber saved successfully";
    }

    public List<Subscriber> fetchAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public Subscriber getSubscriberById(UUID id) {
        List<Subscriber> all = subscriberRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(id)) {
                return all.get(i);
            }
        }
        return null;
    }

    public String updateSubscriberInfo(UUID id, Subscriber updated) {
        Subscriber sub = getSubscriberById(id);
        if (sub == null) {
            return "The subscriber with that id does not exist";
        }
        sub.setFirstName(updated.getFirstName());
        sub.setLastName(updated.getLastName());
        sub.setPhone(updated.getPhone());
        sub.setEmail(updated.getEmail());
        subscriberRepository.save(sub);
        return "Subscriber updated successfully";
    }

    public String removeSubscriber(UUID id) {
        Subscriber sub = getSubscriberById(id);
        if (sub == null) {
            return "The subscriber with that id does not exist";
        }
        subscriberRepository.deleteById(id);
        return "Subscriber deleted successfully";
    }

    public List<Subscriber> getByLastName(String lastName) {
        List<Subscriber> all = subscriberRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
        List<Subscriber> result = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            Subscriber s = all.get(i);
            if (s.getLastName() != null && s.getLastName().equalsIgnoreCase(lastName)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Subscriber> getSubscribersOfBiller(UUID billerId) {
        return subscriberRepository.findSubscribersByBillerId(billerId);
    }
}

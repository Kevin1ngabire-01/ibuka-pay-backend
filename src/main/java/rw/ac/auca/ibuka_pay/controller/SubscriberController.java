package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.service.SubscriberService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/all")
    public List<Subscriber> listSubscribers() {
        return subscriberService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> getSubscriber(@PathVariable UUID id) {
        System.out.println("fetching subscriber with id: " + id);
        Subscriber subscriber = subscriberService.getById(id);
        if (subscriber == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(subscriber);
    }

    @GetMapping("/search")
    public List<Subscriber> searchByLastName(@RequestParam String lastName) {
        return subscriberService.getByLastName(lastName);
    }

    @PostMapping("/save")
    public ResponseEntity<Subscriber> saveSubscriber(@RequestBody Subscriber subscriber) {
        Subscriber saved = subscriberService.create(subscriber);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable UUID id, @RequestBody Subscriber subscriber) {
        Subscriber updated = subscriberService.update(id, subscriber);
        if (updated == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubscriber(@PathVariable UUID id) {
        String result = subscriberService.delete(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }
}

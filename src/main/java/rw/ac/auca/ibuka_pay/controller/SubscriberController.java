package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.service.BillerService;
import rw.ac.auca.ibuka_pay.service.SubscriberService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final BillerService billerService;

    public SubscriberController(SubscriberService subscriberService, BillerService billerService) {
        this.subscriberService = subscriberService;
        this.billerService = billerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> addSubscriber(@RequestBody Subscriber subscriber) {
        // hit this endpoint to register a new subscriber, id is assigned in service
        System.out.println("addSubscriber called - body received: " + subscriber.getFirstName());
        String msg = subscriberService.registerSubscriber(subscriber);
        return ResponseEntity.status(201).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        List<Subscriber> subs = subscriberService.fetchAllSubscribers();
        return ResponseEntity.status(200).body(subs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubscriber(@PathVariable UUID id) {
        Subscriber subscriber = subscriberService.getSubscriberById(id);
        if (subscriber == null) {
            return ResponseEntity.status(404).body("The subscriber with that id does not exist");
        }
        return ResponseEntity.status(200).body(subscriber);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSubscriber(@PathVariable UUID id, @RequestBody Subscriber subscriber) {
        String result = subscriberService.updateSubscriberInfo(id, subscriber);
        if (result.contains("does not exist")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubscriber(@PathVariable UUID id) {
        String result = subscriberService.removeSubscriber(id);
        if (result.contains("does not exist")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Subscriber>> getByLastName(@RequestParam String lastName) {
        List<Subscriber> list = subscriberService.getByLastName(lastName);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/of-biller/{billerId}")
    public ResponseEntity<?> getSubscribersOfBiller(@PathVariable UUID billerId) {
        if (billerService.findBillerById(billerId) == null) {
            return ResponseEntity.status(404).body("The biller with that id does not exist");
        }
        List<Subscriber> list = subscriberService.getSubscribersOfBiller(billerId);
        return ResponseEntity.status(200).body(list);
    }
}

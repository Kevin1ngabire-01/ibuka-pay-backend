package rw.ac.auca.ibuka_pay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.services.SubscriberService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Subscriber subscriber) {
        String result = subscriberService.saveSubscriber(subscriber);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> getAll() {
        List<Subscriber> list = subscriberService.getAllSubscribers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Subscriber> subscriber = subscriberService.getSubscriberById(id);
        if (subscriber.isPresent()) {
            return new ResponseEntity<>(subscriber.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The subscriber with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Subscriber subscriber) {
        String result = subscriberService.updateSubscriber(id, subscriber);
        if (result.equals("The subscriber with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = subscriberService.deleteSubscriber(id);
        if (result.equals("The subscriber with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
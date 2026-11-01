package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Biller;
import rw.ac.auca.ibuka_pay.model.Subscriber;
import rw.ac.auca.ibuka_pay.service.BillerService;
import rw.ac.auca.ibuka_pay.service.SubscriberService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/billers")
public class BillerController {

    private final BillerService billerService;
    private final SubscriberService subscriberService;

    public BillerController(BillerService billerService, SubscriberService subscriberService) {
        this.billerService = billerService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/all")
    public List<Biller> getAllBillers() {
        return billerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biller> getOneBiller(@PathVariable UUID id) {
        Biller biller = billerService.getById(id);
        if (biller == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(biller);
    }

    @GetMapping("/by-bill-category")
    public List<Biller> getByCategory(@RequestParam String name) {
        return billerService.getByCategory(name);
    }

    @GetMapping("/{billerId}/subscribers")
    public List<Subscriber> getSubscribersOfBiller(@PathVariable UUID billerId) {
        return subscriberService.getByBiller(billerId);
    }

    @PostMapping("/save")
    public ResponseEntity<Biller> saveBiller(@RequestBody Biller biller) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billerService.create(biller));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Biller> updateBiller(@PathVariable UUID id, @RequestBody Biller biller) {
        Biller updated = billerService.update(id, biller);
        if (updated == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{billerId}/bill-categories/{billCategoryId}")
    public ResponseEntity<String> addCategoryToBiller(@PathVariable UUID billerId,
                                                       @PathVariable UUID billCategoryId) {
        String result = billerService.addCategory(billerId, billCategoryId);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("already has")) {
            return ResponseEntity.status(409).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{billerId}/pay-code")
    public ResponseEntity<String> changePayCode(@PathVariable UUID billerId,
                                                 @RequestParam int codeNumber) {
        String result = billerService.changePayCode(billerId, codeNumber);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("already taken")) {
            return ResponseEntity.status(409).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeBiller(@PathVariable UUID id) {
        String result = billerService.delete(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }
}

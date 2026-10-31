package rw.ac.auca.ibuka_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Biller;
import rw.ac.auca.ibuka_pay.service.BillerService;
import rw.ac.auca.ibuka_pay.service.SubscriberService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/billers")
public class BillerController {

    @Autowired
    BillerService billerService;

    @Autowired
    SubscriberService subscriberService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveBiller(@RequestBody Biller biller)
    {
        String msg = billerService.createBiller(biller);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", true);
        body.put("message", msg);
        return ResponseEntity.status(201).body(body);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Biller>> getAllBillers() {
        return ResponseEntity.ok(billerService.fetchAllBillers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneBiller(@PathVariable UUID id)
    {
        Biller biller = billerService.findBillerById(id);
        if (biller == null)
        {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("success", false);
            body.put("message", "The biller with that id does not exist");
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(biller);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateBiller(
            @PathVariable UUID id,
            @RequestBody Biller biller) {
        String result = billerService.editBiller(id, biller);
        Map<String, Object> body = new LinkedHashMap<>();
        if (result.contains("does not exist"))
        {
            body.put("success", false);
            body.put("message", result);
            return ResponseEntity.status(404).body(body);
        }
        body.put("success", true);
        body.put("message", result);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteBiller(@PathVariable UUID id) {
        String result = billerService.removeBiller(id);
        Map<String, Object> body = new LinkedHashMap<>();
        if (result.contains("does not exist"))
        {
            body.put("success", false);
            body.put("message", result);
            return ResponseEntity.status(404).body(body);
        }
        body.put("success", true);
        body.put("message", result);
        return ResponseEntity.ok(body);
    }

    // C1 - billers by bill category name
    @GetMapping("/by-bill-category")
    public ResponseEntity<List<Biller>> getByCategory(@RequestParam String name) {
        return ResponseEntity.ok(billerService.getBillersByCategory(name));
    }

    // D3 - add bill category to biller
    @PutMapping("/{billerId}/bill-categories/{billCategoryId}")
    public ResponseEntity<Map<String, Object>> addCategory(
            @PathVariable UUID billerId,
            @PathVariable UUID billCategoryId)
    {
        String result = billerService.addCategoryToBiller(billerId, billCategoryId);
        Map<String, Object> body = new LinkedHashMap<>();
        if (result.contains("does not exist")) {
            body.put("success", false);
            body.put("message", result);
            return ResponseEntity.status(404).body(body);
        }
        if (result.contains("already has"))
        {
            body.put("success", false);
            body.put("message", result);
            return ResponseEntity.status(409).body(body);
        }
        body.put("success", true);
        body.put("message", result);
        return ResponseEntity.ok(body);
    }

    // D4 - move biller to another pay code
    @PatchMapping("/{billerId}/pay-code")
    public ResponseEntity<Map<String, Object>> changePayCode(
            @PathVariable UUID billerId,
            @RequestParam int codeNumber) {
        String result = billerService.changePayCode(billerId, codeNumber);
        Map<String, Object> body = new LinkedHashMap<>();
        if (result.contains("does not exist"))
        {
            body.put("success", false);
            body.put("message", result);
            return ResponseEntity.status(404).body(body);
        }
        if (result.contains("taken")) {
            body.put("success", false);
            body.put("message", result);
            return ResponseEntity.status(409).body(body);
        }
        body.put("success", true);
        body.put("message", result);
        return ResponseEntity.ok(body);
    }

    // C4 - subscribers of a biller
    @GetMapping("/of-biller/{billerId}")
    public ResponseEntity<?> getSubscribersOfBiller(@PathVariable UUID billerId)
    {
        if (billerService.findBillerById(billerId) == null) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("success", false);
            body.put("message", "The biller with that id does not exist");
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(subscriberService.getSubscribersOfBiller(billerId));
    }
}

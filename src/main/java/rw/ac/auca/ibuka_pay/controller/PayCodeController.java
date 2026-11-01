package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.PayCode;
import rw.ac.auca.ibuka_pay.service.PayCodeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pay-codes")
public class PayCodeController {

    private final PayCodeService payCodeService;

    public PayCodeController(PayCodeService payCodeService) {
        this.payCodeService = payCodeService;
    }

    @GetMapping("/all")
    public List<PayCode> getAllPayCodes() {
        return payCodeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayCode> getOnePayCode(@PathVariable UUID id) {
        PayCode payCode = payCodeService.getById(id);
        if (payCode == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(payCode);
    }

    @GetMapping("/free")
    public List<PayCode> getFreePayCodes() {
        return payCodeService.getFreePayCodes();
    }

    @PostMapping("/save")
    public ResponseEntity<PayCode> savePayCode(@RequestBody PayCode payCode) {
        return ResponseEntity.status(HttpStatus.CREATED).body(payCodeService.create(payCode));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PayCode> updatePayCode(@PathVariable UUID id, @RequestBody PayCode payCode) {
        PayCode updated = payCodeService.update(id, payCode);
        if (updated == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayCode(@PathVariable UUID id) {
        String result = payCodeService.delete(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }
}

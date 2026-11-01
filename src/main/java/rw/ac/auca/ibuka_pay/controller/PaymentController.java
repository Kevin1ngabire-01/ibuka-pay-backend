package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Payment;
import rw.ac.auca.ibuka_pay.service.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePayment(@RequestParam UUID billId, @RequestBody Payment payment) {
        String result = paymentService.create(billId, payment);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("only")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/by-subscriber-phone")
    public List<Payment> getByPhone(@RequestParam String phone) {
        return paymentService.getBySubscriberPhone(phone);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable UUID id) {
        String result = paymentService.delete(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }
}

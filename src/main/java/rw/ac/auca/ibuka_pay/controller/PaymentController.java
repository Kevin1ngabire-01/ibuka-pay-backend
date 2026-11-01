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

    @SuppressWarnings("rawtypes")
    @PostMapping("/save")
    public ResponseEntity savePayment(@RequestParam UUID billId, @RequestBody Payment payment) {
        String result = paymentService.savePayment(billId, payment);
        if (result.contains("does not exist")) {
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
        if (result.contains("only for PAID")) {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/subscriber/{phone}")
    public ResponseEntity getBySubscriberPhone(@PathVariable String phone) {
        List<Payment> list = paymentService.getPaymentsBySubscriberPhone(phone);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePayment(@PathVariable UUID id) {
        String result = paymentService.deletePayment(id);
        if (result.contains("does not exist")) {
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}

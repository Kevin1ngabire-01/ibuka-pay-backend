package rw.ac.auca.ibuka_pay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.PayCode;
import rw.ac.auca.ibuka_pay.services.PayCodeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pay-codes")
public class PayCodeController {

    @Autowired
    private PayCodeService payCodeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody PayCode payCode) {
        String result = payCodeService.savePayCode(payCode);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PayCode>> getAll() {
        List<PayCode> list = payCodeService.getAllPayCodes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<PayCode> payCode = payCodeService.getPayCodeById(id);
        if (payCode.isPresent()) {
            return new ResponseEntity<>(payCode.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The pay code with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody PayCode payCode) {
        String result = payCodeService.updatePayCode(id, payCode);
        if (result.equals("The pay code with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = payCodeService.deletePayCode(id);
        if (result.equals("The pay code with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
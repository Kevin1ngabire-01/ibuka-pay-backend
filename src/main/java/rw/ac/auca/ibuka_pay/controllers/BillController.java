package rw.ac.auca.ibuka_pay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.services.BillService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Bill bill) {
        String result = billService.saveBill(bill);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAll() {
        List<Bill> list = billService.getAllBills();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Bill> bill = billService.getBillById(id);
        if (bill.isPresent()) {
            return new ResponseEntity<>(bill.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The bill with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Bill bill) {
        String result = billService.updateBill(id, bill);
        if (result.equals("The bill with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = billService.deleteBill(id);
        if (result.equals("The bill with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
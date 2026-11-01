package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.enums.BillStatus;
import rw.ac.auca.ibuka_pay.model.Bill;
import rw.ac.auca.ibuka_pay.service.BillService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/all")
    public List<Bill> getAllBills() {
        return billService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getOneBill(@PathVariable UUID id) {
        Bill bill = billService.getById(id);
        if (bill == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/by-status")
    public List<Bill> getByStatus(@RequestParam BillStatus status) {
        return billService.getByStatus(status);
    }

    @GetMapping("/between")
    public List<Bill> getBetween(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return billService.getBetweenDates(start, end);
    }

    @GetMapping("/stats/by-status")
    public List<Object[]> countByStatus() {
        return billService.countByStatus();
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBill(@RequestBody Bill bill) {
        String result = billService.create(bill);
        if (result.contains("past") || result.contains("already has")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBill(@PathVariable UUID id, @RequestBody Bill bill) {
        String result = billService.update(id, bill);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam BillStatus status) {
        String result = billService.updateStatus(id, status);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("Cannot change")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID billerId, @RequestParam LocalDate date) {
        int count = billService.cancelForBillerOnDate(billerId, date);
        return ResponseEntity.ok(count + " bills cancelled");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable UUID id) {
        String result = billService.delete(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("has payments")) {
            return ResponseEntity.status(409).body(result);
        }
        return ResponseEntity.ok(result);
    }
}

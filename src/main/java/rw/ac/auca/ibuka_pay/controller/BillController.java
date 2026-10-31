package rw.ac.auca.ibuka_pay.controller;

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

    @PostMapping("/save")
    public ResponseEntity<String> addBill(@RequestBody Bill bill) {
        String msg = billService.addNewBill(bill);
        if (msg.contains("past")) {
            return ResponseEntity.status(400).body(msg);
        }
        if (msg.contains("already has a bill")) {
            return ResponseEntity.status(409).body(msg);
        }
        return ResponseEntity.status(201).body(msg);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.fetchBills();
        return ResponseEntity.status(200).body(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneBill(@PathVariable UUID id) {
        Bill bill = billService.getBillById(id);
        if (bill == null) {
            return ResponseEntity.status(404).body("The bill with that id does not exist");
        }
        return ResponseEntity.status(200).body(bill);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBill(@PathVariable UUID id, @RequestBody Bill bill) {
        String result = billService.modifyBill(id, bill);
        if (result.contains("does not exist")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam BillStatus status) {
        String result = billService.changeStatus(id, status);
        if (result.contains("does not exist")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("Cannot change")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Bill>> getByStatus(@RequestParam BillStatus status) {
        List<Bill> list = billService.getBillsByStatus(status);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Bill>> getBillsBetween(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        List<Bill> list = billService.getBillsBetweenDates(start, end);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> statsByStatus() {
        List<Object[]> result = billService.getBillsCountByStatus();
        return ResponseEntity.status(200).body(result);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID billerId, @RequestParam LocalDate date) {
        String result = billService.cancelBillsForBillerOnDate(billerId, date);
        return ResponseEntity.status(200).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable UUID id) {
        String result = billService.killBill(id);
        if (result.contains("does not exist")) {
            return ResponseEntity.status(404).body(result);
        }
        if (result.contains("has payments")) {
            return ResponseEntity.status(409).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }
}

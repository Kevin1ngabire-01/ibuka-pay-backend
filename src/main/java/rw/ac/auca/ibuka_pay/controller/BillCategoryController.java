package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import rw.ac.auca.ibuka_pay.service.BillCategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bill-categories")
public class BillCategoryController {

    private final BillCategoryService billCategoryService;

    public BillCategoryController(BillCategoryService billCategoryService) {
        this.billCategoryService = billCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> registerCategory(@RequestBody BillCategory billCategory) {
        System.out.println("debugging category post");
        String msg = billCategoryService.addCategory(billCategory);
        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        response.put("status", "created");
        return new ResponseEntity<>(response, HttpStatus.valueOf(201));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> modifyExistingCat(
            @PathVariable UUID id,
            @RequestBody BillCategory billCategory) {

        String result = billCategoryService.updateCategory(id, billCategory);
        Map<String, Object> response = new HashMap<>();

        if (result.contains("does not exist")) {
            response.put("error", result);
            return new ResponseEntity<>(response, HttpStatus.valueOf(404));
        }
        response.put("message", result);
        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> removeCategory(@PathVariable UUID id) {
        String result = billCategoryService.removeCategory(id);
        Map<String, Object> response = new HashMap<>();

        if (result.contains("does not exist")) {
            response.put("error", result);
            return new ResponseEntity<>(response, HttpStatus.valueOf(404));
        }
        response.put("message", result);
        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> showAllCats() {
        List<BillCategory> list = billCategoryService.getAllBillCategories();
        Map<String, Object> envelope = new HashMap<>();
        envelope.put("data", list);
        envelope.put("count", list.size());
        return new ResponseEntity<>(envelope, HttpStatus.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> fetchOneCategory(@PathVariable UUID id) {
        BillCategory category = billCategoryService.getBillCategoryById(id);
        Map<String, Object> response = new HashMap<>();

        if (category == null) {
            response.put("error", "The bill category with that id does not exist");
            return new ResponseEntity<>(response, HttpStatus.valueOf(404));
        }
        response.put("data", category);
        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    // C3 - unused categories query
    @GetMapping("/unused")
    public ResponseEntity<Map<String, Object>> getUnused() {
        List<BillCategory> list = billCategoryService.getUnusedCategories();
        Map<String, Object> envelope = new HashMap<>();
        envelope.put("data", list);
        envelope.put("count", list.size());
        envelope.put("note", "categories with no biller assigned");
        return new ResponseEntity<>(envelope, HttpStatus.valueOf(200));
    }
}

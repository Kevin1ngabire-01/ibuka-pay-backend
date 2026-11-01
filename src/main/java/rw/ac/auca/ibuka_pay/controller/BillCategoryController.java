package rw.ac.auca.ibuka_pay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import rw.ac.auca.ibuka_pay.service.BillCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bill-categories")
public class BillCategoryController {

    private final BillCategoryService billCategoryService;

    public BillCategoryController(BillCategoryService billCategoryService) {
        this.billCategoryService = billCategoryService;
    }

    @GetMapping
    public List<BillCategory> showAll() {
        return billCategoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillCategory> findOne(@PathVariable UUID id) {
        BillCategory cat = billCategoryService.getById(id);
        if (cat == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/unused")
    public List<BillCategory> getUnused() {
        return billCategoryService.getUnusedCategories();
    }

    @PostMapping("/save")
    public ResponseEntity<BillCategory> addNew(@RequestBody BillCategory billCategory) {
        BillCategory saved = billCategoryService.create(billCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BillCategory> editCategory(@PathVariable UUID id, @RequestBody BillCategory billCategory) {
        BillCategory updated = billCategoryService.update(id, billCategory);
        if (updated == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(updated);
    }

    // no delete endpoint - forgot to add it
}

package rw.ac.auca.ibuka_pay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import rw.ac.auca.ibuka_pay.services.BillCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bill-categories")
public class BillCategoryController {

    @Autowired
    private BillCategoryService billCategoryService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody BillCategory billCategory) {
        String result = billCategoryService.saveBillCategory(billCategory);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BillCategory>> getAll() {
        List<BillCategory> list = billCategoryService.getAllBillCategories();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<BillCategory> category = billCategoryService.getBillCategoryById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The bill category with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody BillCategory billCategory) {
        String result = billCategoryService.updateBillCategory(id, billCategory);
        if (result.equals("The bill category with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = billCategoryService.deleteBillCategory(id);
        if (result.equals("The bill category with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
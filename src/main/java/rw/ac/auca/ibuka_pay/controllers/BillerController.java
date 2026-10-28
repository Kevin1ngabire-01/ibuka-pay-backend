package rw.ac.auca.ibuka_pay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.Biller;
import rw.ac.auca.ibuka_pay.services.BillerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/billers")
public class BillerController {

    @Autowired
    private BillerService billerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Biller biller) {
        String result = billerService.saveBiller(biller);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Biller>> getAll() {
        List<Biller> list = billerService.getAllBillers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Biller> biller = billerService.getBillerById(id);
        if (biller.isPresent()) {
            return new ResponseEntity<>(biller.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The biller with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Biller biller) {
        String result = billerService.updateBiller(id, biller);
        if (result.equals("The biller with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = billerService.deleteBiller(id);
        if (result.equals("The biller with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
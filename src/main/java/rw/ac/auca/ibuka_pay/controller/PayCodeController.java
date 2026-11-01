package rw.ac.auca.ibuka_pay.controller;

import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ibuka_pay.model.PayCode;
import rw.ac.auca.ibuka_pay.service.PayCodeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pay-codes")
public class PayCodeController {

    private final PayCodeService payCodeService;

    public PayCodeController(PayCodeService payCodeService) {
        this.payCodeService = payCodeService;
    }

    @PostMapping("/save")
    public Map<String, Object> submitPayCode(@RequestBody PayCode payCode) {
        String msg = payCodeService.addPayCode(payCode);
        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        response.put("status", 201);
        return response;
    }

    @GetMapping("/all")
    public Map<String, Object> fetchRecords() {
        List<PayCode> list = payCodeService.getAllPayCodes();
        Map<String, Object> response = new HashMap<>();
        response.put("data", list);
        response.put("count", list == null ? 0 : list.size());
        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> fetchOneRecord(@PathVariable UUID id) {
        PayCode payCode = payCodeService.getOnePayCode(id);
        Map<String, Object> response = new HashMap<>();
        if (payCode == null) {
            response.put("error", "The pay code with that id does not exist");
            response.put("status", 404);
            return response;
        }
        response.put("data", payCode);
        response.put("status", 200);
        return response;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> modifyRecord(@PathVariable UUID id, @RequestBody PayCode payCode) {
        String result = payCodeService.changePayCode(id, payCode);
        Map<String, Object> response = new HashMap<>();
        if (result.contains("does not exist")) {
            response.put("error", result);
            response.put("status", 404);
            return response;
        }
        response.put("message", result);
        response.put("status", 200);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> eraseItem(@PathVariable UUID id) {
        String result = payCodeService.killPayCode(id);
        Map<String, Object> response = new HashMap<>();
        if (result.contains("does not exist")) {
            response.put("error", result);
            response.put("status", 404);
            return response;
        }
        response.put("message", result);
        response.put("status", 200);
        return response;
    }

    @GetMapping("/free")
    public Map<String, Object> fetchFreeRecords() {
        List<PayCode> list = payCodeService.getFreePayCodes();
        Map<String, Object> response = new HashMap<>();
        response.put("data", list);
        response.put("count", list == null ? 0 : list.size());
        return response;
    }
}

package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.enums.BillStatus;
import rw.ac.auca.ibuka_pay.repository.BillRepository;
import rw.ac.auca.ibuka_pay.repository.BillerRepository;
import rw.ac.auca.ibuka_pay.repository.PayCodeRepository;
import rw.ac.auca.ibuka_pay.repository.PaymentRepository;
import rw.ac.auca.ibuka_pay.repository.SubscriberRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final SubscriberRepository subscriberRepository;
    private final BillerRepository billerRepository;
    private final PayCodeRepository payCodeRepository;
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(
            SubscriberRepository subscriberRepository,
            BillerRepository billerRepository,
            PayCodeRepository payCodeRepository,
            BillRepository billRepository,
            PaymentRepository paymentRepository) {
        this.subscriberRepository = subscriberRepository;
        this.billerRepository = billerRepository;
        this.payCodeRepository = payCodeRepository;
        this.billRepository = billRepository;
        this.paymentRepository = paymentRepository;
    }

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("subscribers", subscriberRepository.count());
        summary.put("billers", billerRepository.count());
        summary.put("freePayCodes", (long) payCodeRepository.findFreePayCodes().size());

        long dueToday = billRepository.findByStatusOrderByDueDateAsc(BillStatus.DUE)
            .stream()
            .filter(b -> b.getDueDate().equals(LocalDate.now()))
            .count();
        summary.put("billsDueToday", dueToday);

        summary.put("upcoming", (long) billRepository.findByStatusOrderByDueDateAsc(BillStatus.UPCOMING).size());
        summary.put("payments", paymentRepository.count());

        return summary;
    }
}

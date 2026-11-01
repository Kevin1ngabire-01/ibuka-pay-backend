package rw.ac.auca.ibuka_pay.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import rw.ac.auca.ibuka_pay.repository.BillCategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BillCategoryService {

    private final BillCategoryRepository billCategoryRepository;

    public BillCategoryService(BillCategoryRepository billCategoryRepository) {
        this.billCategoryRepository = billCategoryRepository;
    }

    public List<BillCategory> getAll() {
        return billCategoryRepository.findAll();
    }

    public BillCategory getById(UUID id) {
        return billCategoryRepository.findById(id).orElse(null);
    }

    public BillCategory create(BillCategory billCategory) {
        return billCategoryRepository.save(billCategory);
    }

    public BillCategory update(UUID id, BillCategory updated) {
        BillCategory existing = getById(id);
        if (existing == null) {
            return null;
        }
        existing.setName(updated.getName());
        return billCategoryRepository.save(existing);
    }

    public String delete(UUID id) {
        BillCategory existing = getById(id);
        if (existing == null) {
            return "Bill category not found";
        }
        billCategoryRepository.deleteById(id);
        return "Deleted successfully";
    }

    public List<BillCategory> getUnusedCategories() {
        return billCategoryRepository.findUnusedCategories();
    }
}

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

    public String addCategory(BillCategory billCategory) {
        billCategoryRepository.save(billCategory);
        return "Bill category saved successfully";
    }

    public List<BillCategory> getAllBillCategories() {
        return billCategoryRepository.findAll();
    }

    public BillCategory getBillCategoryById(UUID id) {
        return billCategoryRepository.findById(id).orElse(null);
    }

    public String updateCategory(UUID id, BillCategory updated) {
        BillCategory category = billCategoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "The bill category with that id does not exist";
        }
        category.setName(updated.getName());
        billCategoryRepository.save(category);
        return "Bill category updated successfully";
    }

    public String removeCategory(UUID id) {
        if (billCategoryRepository.findById(id).isEmpty()) {
            return "The bill category with that id does not exist";
        }
        billCategoryRepository.deleteById(id);
        return "Bill category deleted successfully";
    }

    public List<BillCategory> getUnusedCategories() {
        return billCategoryRepository.findUnusedCategories();
    }
}

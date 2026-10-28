package rw.ac.auca.ibuka_pay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.ibuka_pay.model.BillCategory;
import rw.ac.auca.ibuka_pay.repositories.BillCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillCategoryService {

    @Autowired
    private BillCategoryRepository billCategoryRepository;

    public String saveBillCategory(BillCategory billCategory) {
        billCategoryRepository.save(billCategory);
        return "Bill category saved successfully";
    }

    public List<BillCategory> getAllBillCategories() {
        return billCategoryRepository.findAll();
    }

    public Optional<BillCategory> getBillCategoryById(UUID id) {
        return billCategoryRepository.findById(id);
    }

    public String updateBillCategory(UUID id, BillCategory updated) {
        Optional<BillCategory> existing = billCategoryRepository.findById(id);
        if (existing.isPresent()) {
            BillCategory category = existing.get();
            category.setName(updated.getName());
            billCategoryRepository.save(category);
            return "Bill category updated successfully";
        }
        return "The bill category with that id does not exist";
    }

    public String deleteBillCategory(UUID id) {
        if (billCategoryRepository.existsById(id)) {
            billCategoryRepository.deleteById(id);
            return "Bill category deleted successfully";
        }
        return "The bill category with that id does not exist";
    }
}
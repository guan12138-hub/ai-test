package com.aiingredient.service;

import com.aiingredient.model.*;
import com.aiingredient.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ConsumptionRecordRepository consumptionRepository;
    
    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientCategoryRepository categoryRepository,
                             SupplierRepository supplierRepository,
                             ConsumptionRecordRepository consumptionRepository) {
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.consumptionRepository = consumptionRepository;
    }
    
    public Page<Ingredient> getAllIngredients(Pageable pageable) { return ingredientRepository.findAll(pageable); }
    public List<Ingredient> getAllIngredients() { return ingredientRepository.findAll(); }
    public Ingredient getById(Long id) { return ingredientRepository.findById(id).orElseThrow(); }
    
    @Transactional
    public Ingredient save(Ingredient ingredient) {
        updateIngredientStatus(ingredient);
        return ingredientRepository.save(ingredient);
    }
    
    @Transactional
    public void delete(Long id) { ingredientRepository.deleteById(id); }
    
    public List<IngredientCategory> getAllCategories() { return categoryRepository.findByEnabledTrueOrderBySortOrder(); }
    public IngredientCategory saveCategory(IngredientCategory category) { return categoryRepository.save(category); }
    public void deleteCategory(Long id) { categoryRepository.deleteById(id); }
    
    public List<Supplier> getAllSuppliers() { return supplierRepository.findAll(); }
    public Supplier saveSupplier(Supplier supplier) { return supplierRepository.save(supplier); }
    public void deleteSupplier(Long id) { supplierRepository.deleteById(id); }
    
    @Transactional
    public ConsumptionRecord recordConsumption(Long ingredientId, Double quantity, String type, String remark, Long userId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow();
        ingredient.setStockQuantity(ingredient.getStockQuantity() - quantity);
        if (ingredient.getStockQuantity() < 0) ingredient.setStockQuantity(0.0);
        ingredientRepository.save(ingredient);
        ConsumptionRecord record = new ConsumptionRecord();
        record.setIngredient(ingredient);
        record.setQuantity(quantity);
        record.setType(type);
        record.setRemark(remark);
        record.setRecordDate(LocalDate.now());
        User operator = new User(); operator.setId(userId);
        record.setOperator(operator);
        return consumptionRepository.save(record);
    }
    
    public List<ConsumptionRecord> getConsumptionHistory(Long ingredientId) {
        return consumptionRepository.findByIngredientId(ingredientId);
    }
    
    public void checkExpiryWarnings() {
        List<Ingredient> all = ingredientRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Ingredient i : all) {
            if (i.getExpiryDate() != null) {
                long days = ChronoUnit.DAYS.between(today, i.getExpiryDate());
                if (days < 0) i.setStatus(3); // expired
                else if (days <= 7) i.setStatus(2); // warning
                else i.setStatus(1); // normal
                ingredientRepository.save(i);
            }
        }
    }
    
    private void updateIngredientStatus(Ingredient ingredient) {
        if (ingredient.getExpiryDate() != null) {
            long days = ChronoUnit.DAYS.between(LocalDate.now(), ingredient.getExpiryDate());
            if (days < 0) ingredient.setStatus(3);
            else if (days <= 7) ingredient.setStatus(2);
            else ingredient.setStatus(1);
        }
        if (ingredient.getStockQuantity() <= ingredient.getMinStockQuantity() && ingredient.getStockQuantity() > 0) {
            ingredient.setStatus(2);
        }
    }
}

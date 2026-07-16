package com.aiingredient.service;

import com.aiingredient.dto.DashboardStats;
import com.aiingredient.model.Ingredient;
import com.aiingredient.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository orderRepository;
    private final ConsumptionRecordRepository consumptionRepository;
    private final UserRepository userRepository;
    
    public DashboardService(IngredientRepository ingredientRepository,
                            IngredientCategoryRepository categoryRepository,
                            SupplierRepository supplierRepository,
                            PurchaseOrderRepository orderRepository,
                            ConsumptionRecordRepository consumptionRepository,
                            UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.orderRepository = orderRepository;
        this.consumptionRepository = consumptionRepository;
        this.userRepository = userRepository;
    }
    
    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();
        List<Ingredient> all = ingredientRepository.findAll();
        stats.setTotalIngredients(all.size());
        stats.setExpiredCount(all.stream().filter(i -> i.getStatus() == 3).count());
        stats.setWarningCount(all.stream().filter(i -> i.getStatus() == 2).count());
        stats.setNormalCount(all.stream().filter(i -> i.getStatus() == 1).count());
        stats.setTotalStockValue(all.stream().mapToDouble(i -> i.getStockQuantity() * i.getUnitPrice()).sum());
        stats.setTotalSuppliers(supplierRepository.count());
        stats.setTotalOrders(orderRepository.count());
        stats.setTotalUsers(userRepository.count());
        // Category distribution
        Map<String, Long> catDist = new HashMap<>();
        for (Ingredient i : all) {
            if (i.getCategory() != null) {
                catDist.merge(i.getCategory().getName(), 1L, Long::sum);
            }
        }
        stats.setCategoryDistribution(catDist);
        // Monthly consumption
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(6);
        List<Object[]> monthly = consumptionRepository.getMonthlyConsumption(start, end);
        Map<String, Double> monthlyMap = new LinkedHashMap<>();
        for (Object[] row : monthly) {
            monthlyMap.put((String) row[0], (Double) row[1]);
        }
        stats.setMonthlyConsumption(monthlyMap);
        return stats;
    }
}

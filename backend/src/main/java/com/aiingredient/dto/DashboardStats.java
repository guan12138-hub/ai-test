package com.aiingredient.dto;

import lombok.Data;
import java.util.Map;

@Data
public class DashboardStats {
    private long totalIngredients;
    private long expiredCount;
    private long warningCount;
    private long normalCount;
    private double totalStockValue;
    private long totalSuppliers;
    private long totalOrders;
    private long totalUsers;
    private Map<String, Long> categoryDistribution;
    private Map<String, Double> monthlyConsumption;
}

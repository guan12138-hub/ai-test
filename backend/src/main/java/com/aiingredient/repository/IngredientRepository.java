package com.aiingredient.repository;

import com.aiingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByStatus(Integer status);
    List<Ingredient> findByCategoryId(Long categoryId);
    List<Ingredient> findBySupplierId(Long supplierId);
    @Query("SELECT i FROM Ingredient i WHERE i.expiryDate BETWEEN :start AND :end")
    List<Ingredient> findExpiringIngredients(@Param("start") LocalDate start, @Param("end") LocalDate end);
    @Query("SELECT i FROM Ingredient i WHERE i.expiryDate < :date")
    List<Ingredient> findExpiredIngredients(@Param("date") LocalDate date);
    @Query("SELECT i FROM Ingredient i WHERE i.stockQuantity <= i.minStockQuantity")
    List<Ingredient> findLowStockIngredients();
    @Query("SELECT i.name FROM Ingredient i WHERE i.name LIKE %:keyword%")
    List<String> searchByName(@Param("keyword") String keyword);
    long countByStatus(Integer status);
}

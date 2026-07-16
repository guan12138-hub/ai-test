package com.aiingredient.controller;

import com.aiingredient.dto.ApiResponse;
import com.aiingredient.model.*;
import com.aiingredient.service.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) { this.ingredientService = ingredientService; }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Ingredient>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.getAllIngredients()));
    }
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<Ingredient>>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.getAllIngredients(PageRequest.of(page, size))));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Ingredient>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.getById(id)));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Ingredient>> create(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.save(ingredient)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Ingredient>> update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        ingredient.setId(id);
        return ResponseEntity.ok(ApiResponse.success(ingredientService.save(ingredient)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    // Categories
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<IngredientCategory>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.getAllCategories()));
    }
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<IngredientCategory>> createCategory(@RequestBody IngredientCategory category) {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.saveCategory(category)));
    }
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        ingredientService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    // Consumption
    @PostMapping("/{id}/consume")
    public ResponseEntity<ApiResponse<ConsumptionRecord>> consume(
            @PathVariable Long id,
            @RequestParam Double quantity,
            @RequestParam(defaultValue = "CONSUME") String type,
            @RequestParam(defaultValue = "") String remark,
            @RequestAttribute Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
            ingredientService.recordConsumption(id, quantity, type, remark, userId)));
    }
    @GetMapping("/{id}/consumption")
    public ResponseEntity<ApiResponse<List<ConsumptionRecord>>> getConsumption(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.getConsumptionHistory(id)));
    }
}

package com.aiingredient.controller;

import com.aiingredient.dto.ApiResponse;
import com.aiingredient.model.Supplier;
import com.aiingredient.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final IngredientService ingredientService;
    public SupplierController(IngredientService ingredientService) { this.ingredientService = ingredientService; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Supplier>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.getAllSuppliers()));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Supplier>> create(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(ApiResponse.success(ingredientService.saveSupplier(supplier)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Supplier>> update(@PathVariable Long id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        return ResponseEntity.ok(ApiResponse.success(ingredientService.saveSupplier(supplier)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ingredientService.deleteSupplier(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

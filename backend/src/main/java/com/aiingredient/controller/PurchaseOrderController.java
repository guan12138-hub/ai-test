package com.aiingredient.controller;

import com.aiingredient.dto.ApiResponse;
import com.aiingredient.model.PurchaseOrder;
import com.aiingredient.repository.PurchaseOrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class PurchaseOrderController {
    private final PurchaseOrderRepository orderRepository;
    public PurchaseOrderController(PurchaseOrderRepository orderRepository) { this.orderRepository = orderRepository; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrder>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(orderRepository.findAll()));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrder>> create(@RequestBody PurchaseOrder order) {
        return ResponseEntity.ok(ApiResponse.success(orderRepository.save(order)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrder>> update(@PathVariable Long id, @RequestBody PurchaseOrder order) {
        order.setId(id);
        return ResponseEntity.ok(ApiResponse.success(orderRepository.save(order)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String orderNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    private Double quantity = 0.0;
    private Double totalPrice = 0.0;
    @Column(length = 50)
    private String status; // PENDING, COMPLETED, CANCELLED
    @Column(length = 500)
    private String remark;
    private LocalDate orderDate;
    private LocalDateTime createdAt = LocalDateTime.now();
}

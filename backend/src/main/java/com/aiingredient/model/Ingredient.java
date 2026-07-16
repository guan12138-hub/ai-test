package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 200)
    private String barcode;
    @Column(length = 50)
    private String unit; // kg, g, L, 个, 箱
    private Double stockQuantity = 0.0;
    private Double minStockQuantity = 0.0;
    private Double unitPrice = 0.0;
    private LocalDate productionDate;
    private LocalDate expiryDate;
    @Column(length = 50)
    private String storageMethod; // 冷藏, 冷冻, 常温
    @Column(length = 500)
    private String imageUrl;
    @Column(length = 500)
    private String remark;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private IngredientCategory category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    private Integer status = 1; // 1正常 2预警 3过期
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

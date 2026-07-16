package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "consumption_records")
public class ConsumptionRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    private Double quantity = 0.0;
    @Column(length = 50)
    private String type; // CONSUME, WASTE
    @Column(length = 500)
    private String remark;
    private LocalDate recordDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User operator;
    private LocalDateTime createdAt = LocalDateTime.now();
}

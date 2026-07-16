package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ingredient_categories")
public class IngredientCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(length = 200)
    private String description;
    @Column(length = 500)
    private String imageUrl;
    private Integer sortOrder = 0;
    private Boolean enabled = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}

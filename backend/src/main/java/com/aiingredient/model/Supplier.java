package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 50)
    private String contactPerson;
    @Column(length = 20)
    private String phone;
    @Column(length = 200)
    private String address;
    @Column(length = 500)
    private String remark;
    private Boolean enabled = true;
    private LocalDateTime createdAt = LocalDateTime.now();
}

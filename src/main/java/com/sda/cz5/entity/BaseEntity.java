package com.sda.cz5.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@MappedSuperclass
public class BaseEntity {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // autoincrement
    private int id;
}

package com.sda.cz5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
@Builder
public class Location extends BaseEntity {

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private String cityName;


}

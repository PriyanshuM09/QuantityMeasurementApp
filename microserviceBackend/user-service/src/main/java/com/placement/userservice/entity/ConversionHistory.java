package com.placement.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double input;
    private double output;
}
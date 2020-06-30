package com.ironijunior.orderingsystem.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ProductType type;
    private String name;
    private String description;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

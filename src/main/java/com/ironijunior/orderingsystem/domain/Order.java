package com.ironijunior.orderingsystem.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private OrderStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.order", cascade = CascadeType.PERSIST)
    private Set<OrderItem> items;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Transient
    public int getNumberOfItems() {
        return this.items.size();
    }

}

package com.ironijunior.orderingsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderItem implements Serializable {

    @Id
    private EmbbededId id;

    private Long quantity;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmbbededId implements Serializable {
        @OneToOne
        @JoinColumn(name = "product_id", referencedColumnName = "id")
        private Product product;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id")
        @JsonIgnore
        private Order order;
    }
}

package sparta.jeogiyo.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sparta.jeogiyo.domain.product.entity.Product;


@Table(name = "p_product_orders")
@Getter
@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOrderId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Setter
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Setter
    private int quantity;
}

package sparta.jeogiyo.domain.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.jeogiyo.domain.product.entity.Product;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

@Table(name = "p_carts")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id", nullable = false, columnDefinition = "UUID")
    private UUID cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer cartTotalPrice;

    private Integer quantity;

    @Builder.Default
    private Boolean isDeleted = false;

    public void update(int quantity) {
        this.quantity = quantity;
        this.cartTotalPrice = product.getProductPrice() * quantity;
    }

    public void delete() {
        this.isDeleted = true;
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(this.user.getUsername());
    }

}

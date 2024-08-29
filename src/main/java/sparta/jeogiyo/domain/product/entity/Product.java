package sparta.jeogiyo.domain.product.entity;

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
import lombok.Builder;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

@Entity
@Table(name = "p_products")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", columnDefinition = "uuid", updatable = false)
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "store_name", nullable = false)
    private String productName;

    @Column(name = "store_number", nullable = false)
    private Integer productPrice;

    @Column(name = "is_deleted", nullable = false)
    private boolean Is_deleted = false;

    public void delete(UserDetailsImpl user) {
        this.Is_deleted = true;
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(user.getUser().getUsername());
    }
}

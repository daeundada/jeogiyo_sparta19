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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.jeogiyo.domain.product.dto.response.ProductResponse;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_products")
@Getter
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", columnDefinition = "uuid", updatable = false)
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "product_explain", nullable = true)
    private String productExplain;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    public void delete(UserDetailsImpl user) {
        this.isDeleted = true;
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(user.getUser().getUsername());
    }

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productExplain(productExplain)
                .build();
    }

    public void updateProductDetails(String productName, Integer productPrice,
            String productExplain, Store store) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productExplain = productExplain;
        this.store = store;
    }
}

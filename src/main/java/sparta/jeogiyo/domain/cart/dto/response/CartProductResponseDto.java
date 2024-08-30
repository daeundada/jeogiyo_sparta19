package sparta.jeogiyo.domain.cart.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import sparta.jeogiyo.domain.cart.entity.Cart;
import sparta.jeogiyo.domain.product.entity.Product;
import sparta.jeogiyo.domain.store.domain.Store;

@Getter
@Builder
public class CartProductResponseDto {

    private UUID cartId;

    private UUID storeId;

    private String storeName;

    private UUID productId;

    private String productName;

    private Integer productPrice;

    private Integer quantity;

    public static CartProductResponseDto fromEntity(Cart cart,
            Integer quantity) {
        Product product = cart.getProduct();
        Store store = cart.getStore();
        return CartProductResponseDto.builder()
                .cartId(cart.getCartId())
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .quantity(quantity)
                .build();
    }
}
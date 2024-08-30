package sparta.jeogiyo.domain.product.dto.request;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import sparta.jeogiyo.domain.product.entity.Product;
import sparta.jeogiyo.domain.store.domain.Store;

@Getter
@Setter
public class ProductRequest {

    private UUID productId;
    private UUID storeId;
    private String productName;
    private Integer productPrice;
    private String productExplain;

    public Product toEntity(){
        return Product.builder()
                .productId(productId)
                .storeId(Store.builder().storeId(storeId).build())
                .productName(productName)
                .productPrice(productPrice)
                .productPrice(productPrice)
                .productExplain(productExplain)
                .build();
    }
}

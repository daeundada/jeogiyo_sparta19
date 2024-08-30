package sparta.jeogiyo.domain.product.dto.request;

import lombok.Getter;
import lombok.Setter;
import sparta.jeogiyo.domain.store.domain.Store;

@Getter
@Setter
public class ProductSearchRequest {

    private Store storeId;
    private String productName;
    private Integer productPrice;
}

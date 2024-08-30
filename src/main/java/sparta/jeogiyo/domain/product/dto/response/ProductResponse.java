package sparta.jeogiyo.domain.product.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ProductResponse {

    private UUID productId;
    private String productName;
    private Integer productPrice;
    private String productExplain;
}
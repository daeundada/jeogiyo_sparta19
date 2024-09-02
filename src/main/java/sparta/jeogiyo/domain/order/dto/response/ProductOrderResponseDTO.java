package sparta.jeogiyo.domain.order.dto.response;

import lombok.Getter;
import sparta.jeogiyo.domain.order.entity.ProductOrder;

import java.util.UUID;

@Getter
public class ProductOrderResponseDTO {
    private Long productOrderId;
    private UUID productId;
    private int quantity;
    private String productName; // 필요시 추가

    public ProductOrderResponseDTO(ProductOrder productOrder) {
        this.productOrderId = productOrder.getProductOrderId();
        this.productId = productOrder.getProduct().getProductId();
        this.quantity = productOrder.getQuantity();
        this.productName = productOrder.getProduct().getProductName(); // 필요시 추가
    }
}

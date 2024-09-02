package sparta.jeogiyo.domain.order.dto.response;

import lombok.Getter;
import sparta.jeogiyo.domain.order.entity.Order;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderResponseDTO {

    private UUID orderId;

    private Long userId;

    private UUID storeId;

    private String requirement;

    private Boolean isDeleted;

    List<ProductOrderResponseDTO> productOrderList;

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getUserId();
        this.storeId = order.getStore().getStoreId();
        this.requirement = order.getRequirement();
        this.isDeleted = order.getIsDeleted();
    }

    public OrderResponseDTO(Order order, List<ProductOrderResponseDTO> productOrderList) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getUserId();
        this.storeId = order.getStore().getStoreId();
        this.requirement = order.getRequirement();
        this.isDeleted = order.getIsDeleted();
        this.productOrderList = productOrderList;
    }

}

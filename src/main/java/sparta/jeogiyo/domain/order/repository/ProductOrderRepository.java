package sparta.jeogiyo.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.jeogiyo.domain.order.entity.ProductOrder;

import java.util.List;
import java.util.UUID;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByOrder_OrderId(UUID orderId);
}

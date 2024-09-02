package sparta.jeogiyo.domain.order.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.jeogiyo.domain.cart.entity.Cart;
import sparta.jeogiyo.domain.cart.repository.CartRepository;
import sparta.jeogiyo.domain.order.dto.request.OrderRequestDTO;
import sparta.jeogiyo.domain.order.dto.response.OrderResponseDTO;
import sparta.jeogiyo.domain.order.dto.response.ProductOrderResponseDTO;
import sparta.jeogiyo.domain.order.entity.Order;
import sparta.jeogiyo.domain.order.entity.ProductOrder;
import sparta.jeogiyo.domain.order.repository.OrderRepository;
import sparta.jeogiyo.domain.order.repository.ProductOrderRepository;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.CustomException;
import sparta.jeogiyo.global.response.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public void createOrder(OrderRequestDTO orderRequestDTO, UserDetailsImpl userDetails) {

        List<Cart> cartList = cartRepository.findCartsByUserId(userDetails.getUser().getUserId());

        Order order = new Order();

        order.setUser(userDetails.getUser());
        order.setStore(cartList.get(0).getStore());
        order.setRequirement(orderRequestDTO.getRequirement());
        order.setCreatedBy(userDetails.getUser().getUsername());

        orderRepository.save(order);

        for (Cart cart : cartList) {

            ProductOrder productOrder = new ProductOrder();

            productOrder.setOrder(order);
            productOrder.setProduct(cart.getProduct());
            productOrder.setQuantity(cart.getQuantity());

            productOrderRepository.save(productOrder);
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrder(UserDetailsImpl userDetails, int page, int size, String sort) {

        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Order.by(sortParams[0]).with(Sort.Direction.fromString(sortParams[1])));

        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<Order> orderPage = orderRepository.findByUser_UserId(userDetails.getUser().getUserId(), pageable);

        return orderPage.stream()
                .map(OrderResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrder(UUID orderId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        List<ProductOrder> productOrderList = productOrderRepository.findByOrder_OrderId(orderId);


        return new OrderResponseDTO(order,
                productOrderList.stream()
                .map(ProductOrderResponseDTO::new)
                .toList());
    }

    @Transactional
    public void deleteOrder(UUID orderId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        order.setIsDeleted(true);
        order.setDeletedAt(LocalDateTime.now());
        order.setDeletedBy(order.getUser().getNickname());

        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO searchOrder(String orderNumber) {

        UUID uuid = UUID.fromString(orderNumber);

        Order order = orderRepository.findByOrderId(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        return new OrderResponseDTO(order);
    }

}

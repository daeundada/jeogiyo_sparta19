package sparta.jeogiyo.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.jeogiyo.domain.order.service.OrderService;
import sparta.jeogiyo.global.response.ApiResponse;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse<Void>> CreateOrder(@PathVariable("cartId") Long cartId) {

        orderService.CreateOrder(cartId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("주문 생성 성공",null));
    }

}

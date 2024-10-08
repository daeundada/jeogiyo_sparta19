package sparta.jeogiyo.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.jeogiyo.domain.order.dto.request.OrderRequestDTO;
import sparta.jeogiyo.domain.order.dto.response.OrderResponseDTO;
import sparta.jeogiyo.domain.order.service.OrderService;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        orderService.createOrder(orderRequestDTO, userDetails);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("주문 생성 성공", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrder(
            @PageableDefault
                    (size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<OrderResponseDTO> orderResponseDTOS =
                orderService.getAllOrder(userDetails, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("주문 전체 조회 성공", orderResponseDTOS));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrder(
            @PathVariable UUID orderId) {

        OrderResponseDTO orderResponseDTO = orderService.getOrder(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("주문 상세 조회 성공", orderResponseDTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @PathVariable UUID orderId) {

        orderService.deleteOrder(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("주문 삭제 성공", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> searchOrder(
            @RequestParam(name = "orderId") String orderId) {

        OrderResponseDTO orderResponseDTO = orderService.searchOrder(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("주문 검색 성공", orderResponseDTO));
    }
}

package sparta.jeogiyo.domain.cart.controller;

import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.jeogiyo.domain.cart.dto.request.CartCreateRequestDto;
import sparta.jeogiyo.domain.cart.dto.request.CartUpdateRequestDto;
import sparta.jeogiyo.domain.cart.dto.response.CartResponseDto;
import sparta.jeogiyo.domain.cart.service.CartService;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.ApiResponse;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addCart(@RequestBody @Valid CartCreateRequestDto requestDto) {
        CartResponseDto cartResponseDto = cartService.addCart(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("장바구니에 성공적으로 담겼습니다.", cartResponseDto));
    }

    @PutMapping("/products")
    public ResponseEntity<Object> updateCart(@RequestBody @Valid CartUpdateRequestDto requestDto) {
        CartResponseDto cartResponseDto = cartService.updateCart(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("수정되었습니다.", cartResponseDto));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable UUID productId) {
        cartService.deleteProductFromCart(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteAllProductsFromCart(@AuthenticationPrincipal UserDetailsImpl user) {
        cartService.deleteAllProductsFromCart(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Object> getUserCarts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CartResponseDto cart = cartService.getUserCarts(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of("장바구니 목록", cart));
    }

}
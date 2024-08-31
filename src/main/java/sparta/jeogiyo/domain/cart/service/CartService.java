package sparta.jeogiyo.domain.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.jeogiyo.domain.cart.dto.request.CartCreateRequestDto;
import sparta.jeogiyo.domain.cart.dto.request.CartUpdateRequestDto;
import sparta.jeogiyo.domain.cart.dto.response.CartProductResponseDto;
import sparta.jeogiyo.domain.cart.dto.response.CartResponseDto;
import sparta.jeogiyo.domain.cart.entity.Cart;
import sparta.jeogiyo.domain.cart.repository.CartRepository;
import sparta.jeogiyo.domain.product.entity.Product;
import sparta.jeogiyo.domain.product.repository.ProductRepository;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.repository.StoreRepository;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.response.CustomException;
import sparta.jeogiyo.global.response.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public CartResponseDto getUserCarts(User user) {
        log.info("User ID: {}의 장바구니를 조회합니다.", user.getUserId());
        List<Cart> cartList = cartRepository.findCartsByUserId(user.getUserId());
        if (cartList.isEmpty()) {
            log.warn("User ID: {}의 장바구니가 비어 있습니다.", user.getUserId());
            throw new CustomException(ErrorCode.CART_IS_EMPTY);
        }

        List<CartProductResponseDto> productResponseDtoList = new ArrayList<>();
        int totalPrice = 0;
        int totalQuantity = 0;

        for (Cart cart : cartList) {
            totalQuantity += cart.getQuantity();
            totalPrice += cart.getCartTotalPrice();
            productResponseDtoList.add(CartProductResponseDto.fromEntity(cart, cart.getQuantity()));
        }

        log.info("사용자 ID: {}의 장바구니를 반환합니다. 총 가격: {}, 총 수량: {}",
                user.getUserId(), totalPrice, totalQuantity);
        return CartResponseDto.fromEntity(user, productResponseDtoList, totalPrice, totalQuantity);
    }

    @Transactional
    public CartResponseDto addCart(CartCreateRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        log.info("User ID: {}의 장바구니에 제품 ID: {}를 추가합니다.", user.getUserId(),
                requestDto.getProductId());

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> {
                    log.error("Product ID: {}를 찾을 수 없습니다.", requestDto.getProductId());
                    return new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
                });

        if (cartRepository.findCartByProductIdAndUserID(product.getProductId(), user.getUserId())
                .isPresent()) {
            log.warn("User ID: {}의 장바구니에 제품 ID: {}가 이미 존재합니다.", user.getUserId(),
                    product.getProductId());
            throw new CustomException(ErrorCode.CART_PRODUCT_ALREADY_EXIST);
        }

        Store store = storeRepository.findById(product.getStore().getStoreId())
                .orElseThrow(() -> {
                    log.error("Store ID: {}를 찾을 수 없습니다.", product.getStore().getStoreId());
                    return new CustomException(ErrorCode.STORE_ID_NOT_FOUND);
                });

        int quantity = requestDto.getQuantity();
        int cartPrice = product.getProductPrice() * quantity;

        Cart cart = Cart.builder()
                .user(user)
                .store(store)
                .product(product)
                .cartTotalPrice(cartPrice)
                .quantity(quantity)
                .build();

        cartRepository.save(cart);
        log.info("User ID: {}의 장바구니에 Product ID: {}가 추가되었습니다.", user.getUserId(),
                product.getProductId());

        return getUserCarts(user);
    }

    @Transactional
    public CartResponseDto updateCart(CartUpdateRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        log.info("User ID: {}의 장바구니에서 Product ID: {}를 업데이트합니다.", user.getUserId(),
                requestDto.getProductId());

        Cart cart = cartRepository.findCartByProductIdAndUserID(requestDto.getProductId(),
                        user.getUserId())
                .orElseThrow(() -> {
                    log.warn("User ID: {}의 장바구니가 비어 있습니다.", user.getUserId());
                    return new CustomException(ErrorCode.CART_IS_EMPTY);
                });

        cart.update(requestDto.getQuantity());
        cartRepository.save(cart);
        log.info("User ID: {}의 장바구니에서 Product ID: {}가 업데이트되었습니다.", user.getUserId(),
                requestDto.getProductId());

        return getUserCarts(user);
    }

    @Transactional
    public void deleteAllProductsFromCart(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        log.info("User ID: {}의 장바구니에서 모든 제품을 삭제합니다.", user.getUserId());

        List<Cart> cartList = cartRepository.findCartsByUserId(user.getUserId());
        if (cartList.isEmpty()) {
            log.warn("User ID: {}의 장바구니에 삭제할 제품이 없습니다.", user.getUserId());
            throw new CustomException(ErrorCode.CART_IS_EMPTY);
        }
        List<Cart> deletedCart = new ArrayList<>();
        for (Cart cart : cartList) {
            cart.delete();
            deletedCart.add(cart);
        }

        cartRepository.saveAll(deletedCart);
        log.info("User ID: {}의 장바구니에서 모든 Product가 삭제되었습니다.", user.getUserId());
    }

    @Transactional
    public void deleteProductFromCart(UUID productId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        log.info("User ID: {}의 장바구니에서 Product ID: {}를 삭제합니다.", user.getUserId(), productId);

        Cart cart = cartRepository.findCartByProductIdAndUserID(productId, user.getUserId())
                .orElseThrow(() -> {
                    log.warn("User ID: {}의 장바구니가 비어 있거나, Product ID: {}를 찾을 수 없습니다.",
                            user.getUserId(), productId);
                    return new CustomException(ErrorCode.CART_IS_EMPTY);
                });

        cart.delete();
        log.info("User ID: {}의 장바구니에서 Product ID: {}가 삭제되었습니다.", user.getUserId(), productId);
    }
}
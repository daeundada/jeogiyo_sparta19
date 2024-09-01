package sparta.jeogiyo.domain.cart.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import sparta.jeogiyo.domain.user.entity.User;

@Getter
@Builder
public class CartResponseDto {

    private Long userId;

    private List<CartProductResponseDto> products;

    private Integer cartTotalPrice;

    private Integer totalQuantity;

    public static CartResponseDto fromEntity(User user,
            List<CartProductResponseDto> productResponseDtoList, int cartTotalPrice, int totalQuantity) {

        return CartResponseDto.builder()
                .userId(user.getUserId())
                .cartTotalPrice(cartTotalPrice)
                .totalQuantity(totalQuantity)
                .products(productResponseDtoList)
                .build();
    }
}

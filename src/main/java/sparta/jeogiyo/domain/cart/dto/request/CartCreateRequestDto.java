package sparta.jeogiyo.domain.cart.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CartCreateRequestDto {

    @NotEmpty
    private UUID storeId;

    @NotEmpty
    private UUID productId;

    @NotNull
    @Size(min = 1, max = 99)
    private int quantity;

}
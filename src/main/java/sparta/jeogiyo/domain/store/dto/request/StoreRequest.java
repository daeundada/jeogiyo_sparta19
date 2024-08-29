package sparta.jeogiyo.domain.store.dto.request;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.domain.StoreCategory;
import sparta.jeogiyo.domain.user.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequest {

    private User userId;
    private String storeName;
    private String storeNumber;
    private StoreCategory category;

    public Store toEntity() {
        return Store.builder()
                .user(userId)
                .storeName(storeName)
                .storeNumber(storeNumber)
                .category(category)
                .build();
    }
}

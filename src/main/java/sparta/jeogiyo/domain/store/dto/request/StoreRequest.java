package sparta.jeogiyo.domain.store.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.domain.StoreCategory;
import sparta.jeogiyo.domain.user.UserDetailsImpl;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequest {

    private Long userId;
    private String storeName;
    private String storeNumber;
    private StoreCategory category;

    public Store toEntity(UserDetailsImpl userDetails) {
        return Store.builder()
                .user(userDetails.getUser())
                .storeName(storeName)
                .storeNumber(storeNumber)
                .category(category)
                .build();
    }
}

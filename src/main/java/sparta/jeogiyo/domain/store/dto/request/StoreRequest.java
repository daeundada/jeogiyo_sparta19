package sparta.jeogiyo.domain.store.dto.request;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.domain.StoreCategory;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class StoreRequest {

    private UUID storeId;
    private String storeName;
    private String storeNumber;
    private StoreCategory category;

    public Store toEntity(){
        return Store.builder()
                .storeId(storeId)
                .storeName(storeName)
                .storeNumber(storeNumber)
                .category(category)
                .build();
    }
}

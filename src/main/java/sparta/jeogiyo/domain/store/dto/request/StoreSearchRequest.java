package sparta.jeogiyo.domain.store.dto.request;

import lombok.Getter;
import lombok.Setter;
import sparta.jeogiyo.domain.store.domain.StoreCategory;

@Getter
@Setter
public class StoreSearchRequest {

    private String storeName;
    private String storeNumber;
    private StoreCategory category;
}

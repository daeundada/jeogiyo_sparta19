package sparta.jeogiyo.domain.store.dto.request;

import lombok.Getter;
import sparta.jeogiyo.domain.store.domain.StoreCategory;

@Getter
public class StoreSearchRequest {

    private String storeName;
    private String storeNumber;
    private StoreCategory category;
    private String sortBy;
    private String sortOrder;
    private int pageSize;
    private int pageNumber;
}

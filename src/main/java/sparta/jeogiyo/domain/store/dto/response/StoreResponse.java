package sparta.jeogiyo.domain.store.dto.response;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sparta.jeogiyo.domain.store.domain.StoreCategory;

@AllArgsConstructor
@Getter
@Builder
public class StoreResponse implements Serializable {

    private UUID storeId;
    private String storeName;
    private String storeNumber;
    private StoreCategory category;
}

package sparta.jeogiyo.domain.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store extends BaseTimeEntity {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)    //hibernate 6.5버전 이후 GenericGenerator 대신 JdbcTypeCode로 설정
    @Column(name = "store_id", updatable = false, nullable = false)
    private UUID storeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_number", nullable = false)
    private String storeNumber;

    @Column(name = "category", nullable = false)
    private StoreCategory category;

    @Column(name = "is_deleted", nullable = false)
    private boolean Is_deleted = false;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public StoreResponse toResponse() {
        return StoreResponse.builder()
                .storeName(storeName)
                .storeNumber(storeNumber)
                .category(category)
                .build();
    }
}

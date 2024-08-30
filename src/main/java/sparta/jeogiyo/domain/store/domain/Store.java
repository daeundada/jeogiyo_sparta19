package sparta.jeogiyo.domain.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

@Builder
@Entity
@Table(name = "p_stores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id", columnDefinition = "uuid", updatable = false)
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

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean Is_deleted = false;

    public StoreResponse toResponse() {
        return StoreResponse.builder()
                .storeId(storeId)
                .storeName(storeName)
                .storeNumber(storeNumber)
                .category(category)
                .build();
    }

    public void delete(UserDetailsImpl user) {
        this.Is_deleted = true;
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(user.getUser().getUsername());
    }
}

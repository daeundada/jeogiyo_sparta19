package sparta.jeogiyo.domain.store.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sparta.jeogiyo.domain.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID>,
        JpaSpecificationExecutor<Store> {

    Optional<Store> findBystoreNumber(String storeNumber);

    Optional<Store> findBystoreName(String storeName);

    Store findStoreByUser_UserId(Long userId);
}

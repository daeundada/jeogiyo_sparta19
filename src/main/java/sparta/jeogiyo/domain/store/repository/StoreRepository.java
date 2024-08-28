package sparta.jeogiyo.domain.store.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.user.entity.User;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID>,
        JpaSpecificationExecutor<Store> {

    Optional<User> findBystoreNumber(String storeNumber);

    Optional<User> findBystoreName(String storeName);
}

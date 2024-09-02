package sparta.jeogiyo.domain.cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sparta.jeogiyo.domain.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = {"user", "product", "store"})
    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId AND c.user.isDeleted = false")
    List<Cart> findCartsByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c JOIN c.product p JOIN c.store s WHERE c.product.productId = :productId AND c.user.userId = :userId AND c.isDeleted = false")
    Optional<Cart> findCartByProductIdAndUserID(@Param("productId") UUID productId,
            @Param("userId") Long userId);

}
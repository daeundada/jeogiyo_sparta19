package sparta.jeogiyo.domain.product.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.jeogiyo.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}

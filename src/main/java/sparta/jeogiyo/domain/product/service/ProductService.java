package sparta.jeogiyo.domain.product.service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.jeogiyo.domain.product.dto.request.ProductSearchRequest;
import sparta.jeogiyo.domain.product.dto.request.ProductRequest;
import sparta.jeogiyo.domain.product.dto.response.ProductResponse;
import sparta.jeogiyo.domain.product.entity.Product;
import sparta.jeogiyo.domain.product.repository.ProductRepository;
import sparta.jeogiyo.domain.store.repository.StoreRepository;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.CustomException;
import sparta.jeogiyo.global.response.ErrorCode;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Product addProduct(ProductRequest productRequest) {

        validateAddProduct(productRequest);

        return productRepository.save(productRequest.toEntity());
    }

    private void validateAddProduct(ProductRequest productRequest) {
        //중복처리해야 할 내용들 ,,,,
    }

    @Transactional(readOnly = true)
    public Product findProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.PRODUCT_ID_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(int page, int size, String sortBy, boolean isAsc) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(Product::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(ProductSearchRequest request, int page, int size, String sortBy, boolean isAsc) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> spec = buildSpecification(request);

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        return productPage.map(Product::toResponse);
    }

    private Specification<Product> buildSpecification(ProductSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            query.where(predicates.toArray(new Predicate[0]));

        return query.getRestriction();};
    }

    @Transactional
    public Product updateProduct(UUID storeId, ProductRequest request) {
        Product product = null;
        return product;
    }

    @Transactional
    public Product deleteProduct(UUID productId, UserDetailsImpl user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        product.delete(user);
        return productRepository.save(product);
    }
}

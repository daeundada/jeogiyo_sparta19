package sparta.jeogiyo.domain.product.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sparta.jeogiyo.domain.product.dto.request.ProductRequest;
import sparta.jeogiyo.domain.product.dto.request.ProductSearchRequest;
import sparta.jeogiyo.domain.product.dto.response.ProductResponse;
import sparta.jeogiyo.domain.product.entity.Product;
import sparta.jeogiyo.domain.product.service.ProductService;
import sparta.jeogiyo.domain.store.service.StoreService;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.ApiResponse;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(
            @RequestBody ProductRequest productRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID storeId = storeService.findByUserId(userDetails);
        productRequest.setStoreId(storeId);
        Product product = productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("상품 생성을 성공하였습니다.", product.toResponse()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable UUID productId) {
        Product product = productService.findProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("단건 조회 성공하였습니다.", product.toResponse()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProduct(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        Page<ProductResponse> productResponseList = productService.findAll(page, size, sortBy,
                isAsc);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("상품 조회를 성공하였습니다.", productResponseList));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> searchRequest(
            ProductSearchRequest request,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        Page<ProductResponse> productSearchResponseList = productService.searchProducts(request,
                page, size,
                sortBy, isAsc);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("상품 검색을 성공하였습니다.", productSearchResponseList));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateStore(@PathVariable UUID storeId,
            @RequestBody ProductRequest request) {
        Product updatedProduct = productService.updateProduct(storeId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("상품 내용 수정에 성공하였습니다.", updatedProduct.toResponse()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteStore(@PathVariable UUID storeId,
            @AuthenticationPrincipal UserDetailsImpl user) {
        Product deletedproduct = productService.deleteProduct(storeId, user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of("삭제 성공하였습니다.", deletedproduct.toResponse()));
    }
}

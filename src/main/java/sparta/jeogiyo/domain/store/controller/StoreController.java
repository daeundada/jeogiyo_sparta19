package sparta.jeogiyo.domain.store.controller;

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
import sparta.jeogiyo.domain.store.dto.request.StoreRequest;
import sparta.jeogiyo.domain.store.dto.request.StoreSearchRequest;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.service.StoreService;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.response.ApiResponse;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ApiResponse<StoreResponse>> addStore(
            @RequestBody StoreRequest storeRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        storeRequest.setUserId(user.getUser());
        Store store = storeService.addStore(storeRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("가게 생성을 성공하였습니다.", store.toResponse()));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable UUID storeId) {
        Store store = storeService.findStore(storeId);
        if (store == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(store.toResponse());
    }

    @GetMapping
    public ResponseEntity<Page<StoreResponse>> getStore(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        Page<StoreResponse> storeResponseList = storeService.findAll(page, size, sortBy, isAsc);
        return ResponseEntity.status(HttpStatus.OK).body(storeResponseList);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StoreResponse>> searchStore(
            StoreSearchRequest request,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        Page<StoreResponse> storeSearchResponseList = storeService.searchStores(request, page, size,
                sortBy, isAsc);
        return ResponseEntity.status(HttpStatus.OK).body(storeSearchResponseList);
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable UUID storeId,
            @RequestBody StoreRequest patchRequest) {
        Store updatedStore = storeService.updateStore(storeId, patchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStore.toResponse());
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<StoreResponse> deleteStore(@PathVariable UUID storeId) {
        Store deletedStore = storeService.deleteStore(storeId);
        return ResponseEntity.ok(deletedStore.toResponse());
    }

}

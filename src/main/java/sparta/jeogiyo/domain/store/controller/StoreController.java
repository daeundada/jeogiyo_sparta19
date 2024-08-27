package sparta.jeogiyo.domain.store.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.jeogiyo.domain.store.dto.request.StoreRequest;
import sparta.jeogiyo.domain.store.dto.request.StoreSearchRequest;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.service.StoreService;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreResponse> addStore(@RequestBody StoreRequest storeRequest) {
        Store store = storeService.addStore(storeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(store.toResponse());

    }


    @GetMapping
    public ResponseEntity<List<StoreResponse>> getStore(){
        List<StoreResponse> storeResponseList = storeService.findAll();
        return ResponseEntity.ok(storeResponseList);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable UUID storeId){
        Store store = storeService.findStore(storeId);
        if (store == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(store.toResponse());
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<StoreResponse> deleteStore(@PathVariable UUID storeId){
        Store deletedStore = storeService.deleteStore(storeId);
        return ResponseEntity.ok(deletedStore.toResponse());
    }

    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponse> patchStore(@PathVariable UUID storeId, @RequestBody StoreRequest patchRequest){
        Store updatedStore = storeService.patchStore(storeId, patchRequest);
        return ResponseEntity.ok(updatedStore.toResponse());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Store>> searchStore(StoreSearchRequest request){
        Page<Store> stores = storeService.searchStores(request);
        return ResponseEntity.ok(stores);
    }
}

package sparta.jeogiyo.domain.store.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import sparta.jeogiyo.domain.store.dto.request.StoreRequest;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.repository.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Store addStore(StoreRequest storeRequest) {
        return storeRepository.save(storeRequest.toEntity());
    }

    //get 메소드들의 경우 읽기 전용이기때문에 readOnly 로 성능 최적화를 ,,, 노려봅니다,,,,
    @Transactional(readOnly = true)
    public List<StoreResponse> findAll() {
        return storeRepository.findAll().stream()
                .map(Store::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Store findStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + storeId));
    }

    @Transactional
    public Store deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + storeId));
        //삭제 요청시 내부로직은 patch로 동작해 상태만 '삭제' 상태로 변경됩니다.
        store.setIs_deleted(true);
        return storeRepository.save(store);
    }

    @Transactional
    public Store patchStore(UUID storeId, StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + storeId));

        if (storeRequest.getStoreName() != null) {
            store.setStoreName(storeRequest.getStoreName());
        }
        if (storeRequest.getStoreNumber() != null) {
            store.setStoreNumber(storeRequest.getStoreNumber());
        }
        if (storeRequest.getCategory() != null){
            store.setCategory(storeRequest.getCategory());
        }

        return storeRepository.save(store);
    }
}
